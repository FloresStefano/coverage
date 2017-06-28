package it.addvalue.coverage;

import it.addvalue.coverage.bean.Allocation;
import it.addvalue.coverage.bean.Input;
import it.addvalue.coverage.bean.Output;
import it.addvalue.coverage.bean.Plan;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.core.CoverageGenerator;
import it.addvalue.coverage.mock.repositories.GlobalRepository;
import it.addvalue.coverage.mock.utils.BinaryUtils;
import it.addvalue.coverage.mock.utils.CsvUtils;
import it.addvalue.coverage.mock.utils.XmlUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static it.addvalue.coverage.mock.utils.CsvUtils.csvadd;
import static it.addvalue.coverage.mock.utils.CsvUtils.csvloop;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class CoverageTest {

	private static GlobalRepository db;
	private static Input            input;

	@BeforeClass
	public static void init() {
		input = XmlUtils.deserialize("coverage_data.xml", Input.class);
		db = BinaryUtils.deserialize("repository_data.bin", GlobalRepository.class);
	}

	// A fronte di un input dev'essere prodotto un output
	@Test
	public void forEachInputAnOutputMustBeGenerated() {
		CoverageGenerator generator = new CoverageGenerator();
		Output output = generator.generate(input);
		assertThat(input, is(notNullValue()));
		assertThat(output, is(notNullValue()));
	}

	// L'input deve contenere tutti i giorni di un anno
	@Test
	public void theInputMustContainAllYearDays() {
		// VARIABILE= giorni dell'anno calcolato
		assertThat(input.getCalendars(), hasSize(equalTo(365)));
	}

	// Per ogni giorno di input devono essere specificati quali servizi sono
	// attivi e quali sono le loro caratteristiche in termini di expected call
	// ecc.. Ogni servizio attivo è presente.
	@Test
	public void everydayAllServicesMustBeSpecified() {
		for (PlanCalendar pc : input.getCalendars()) {
			assertThat(pc.getDetails(), hasSize(db.allServices().size()));
		}
	}

	// Per ogni giorno, per uno dei servizi attivi, si possono verificare picchi
	// rispetto la media dovuti a marker speciali. Di default ci sono le
	// caratteristiche base dei servizi ma potrei avere dei marker speciali,
	@Test
	public void workloadPeakMarkersMayBePresent() {
		// VARIABILE = minimo e massimo numero di marker accettati (0 e 365)
		for (PlanCalendar pc : input.getCalendars()) {
			for (PlanCalendarDetail pcm : pc.getDetails()) {
				assertThat(pcm.getMarkerMultiplier(), is(notNullValue()));
			}
		}
	}

	// le telefonate attese totali giornaliere devono coprire la somma delle telefonate giornaliere previste dei vari servizi
	@Test
	public void totalExpectedDailyCallsGteSumOfServiceDailyCalls() {
		// VARIABILE = la soglia accettabile di copertura in percentuale
		for (PlanCalendar day : input.getCalendars()) {
			int sumServiceDailyCalls = 0;
			for (PlanCalendarDetail detail : day.getDetails()) {
				Service service = db.service(detail.getIdService());
				sumServiceDailyCalls += service.getDailyCalls();
			}
			assertThat(day.getTotalExpectedCalls(), is(greaterThanOrEqualTo(sumServiceDailyCalls)));
		}
	}

	// le telefonate attese totali giornaliere devono coprire la somma delle telefonate giornaliere previste dei vari servizi
	@Test
	public void totalExpectedDailyCallsDetailGteSumOfServiceDailyCallsDetail() {
		// VARIABILE = la soglia accettabile di copertura in percentuale
		for (PlanCalendar day : input.getCalendars()) {
			String sumServiceDailyCallsDetail = "0,0,0,0,0,0";
			for (PlanCalendarDetail detail : day.getDetails()) {
				Service service = db.service(detail.getIdService());
				sumServiceDailyCallsDetail = csvadd(sumServiceDailyCallsDetail, service.getDailyCallsDetail());
			}
			csvloop(day.getTotalExpectedCallsDetail(), sumServiceDailyCallsDetail, new CsvUtils.Action() {
				public void perform(int csv1Value, int csv2Value) {
					assertThat(csv1Value, is(greaterThanOrEqualTo(csv2Value)));
				}
			});
		}
	}

	@Test
	public void theStaffListMustBeNotEmpty() {
		assertThat(input.getStaffs(), is(not(empty())));
	}

	// L'input deve contenere oltre ai giorni, la lista degli utenti per i quali
	// si vuole trovare il turno idoneo.
	// Per ogni utente c'è la lista dei turni ammissibili fra i quali scegliere.
	@Test
	public void theWorkshiftListOfEachStaffMemberMustBeNotEmpty() {
		// VARIABILE = numero m di turni assegnabili (1 turno)
		for (Staff staff : input.getStaffs()) {
			assertThat(staff.getIdWorkshifts(), is(not(empty())));
		}
	}

	// Ad un utente puo' essere dato solo un turno coerente col suo contratto
	@Test
	public void staffAndWorkshifByContractTest() {
		for (Staff staff : input.getStaffs()) {
			for (Workshift workshift : db.allWorkshifts()) {
				assertThat(workshift.getContractName(), is(equalTo(staff.getContractName())));
			}
		}
	}

	// Per ogni utente c'è la lista dei servizi ai quali l'utente è formato con
	// le caratteristiche di quanto è bravo e quali sono le sue performance.
	// Un utente è formato ad un sottoinsieme dei servizi totali del callcenter.
	// Da 1 a 4 su 10 totali.
	@Test
	public void staffTest() {
		// VARIABILE = numero minimo e massimo di servizi su cui un utente di
		// call center
		// e' formato (da 1 a 4 servizi)

		for (Staff staff : input.getStaffs()) {
			assertThat(staff.getSkills(), is(not(empty())));
			assertThat(staff.getSkills(), hasSize(lessThan(db.allServices().size())));
		}
	}

	// Per ogni giorno in output devono essere soddisfatte l'expected call di
	// ogni servizio attivo. Questo significa che la somma delle telefonate
	// gestite dagli utenti assegnati a quel servizio deve coprire il numero di
	// expected call
	@Test
	public void expectedCallsForServiceTest() {
		// TODO
		// VARIABILE = soglia o percentuale accettabile (100%)
	}

	// Per un giorno deve essere presente almeno un team leader in ogni fascia
	// oraria. Questo significa che almeno un team leader sia sempre presente
	// nel call center durante il giorno.
	@Test
	public void teamleaderPresenceTest() {
		// TODO
		// VARIABILE = numero minimo di teamleader presenti (1 TL)
	}

	// La pianificazione deve soddisfare ogni giorno ma deve ragionare a a slot
	// di 7 gioni. Ovvero ad ogni utente non si può cambiare turno prima di 7
	// giorni consecutivi dello stesso turno. Quindi si stabiliscono moduli
	// settimanali non giornalieri.
	@Test
	public void weekForEachDaysTest() {
		// TODO
		// VARIABILE = numero minimo giorni con lo stesso turno (7gg)
	}

	@Test
	public void outputTest() {
		CoverageGenerator generator = new CoverageGenerator();

		int maxSolutions = 4;

		generator.setMaxSolutions(maxSolutions);
		generator.setFullSearch(false);
//		generator.getSolver().useMaintainingArcConsistencyPolicy(false);

		Output output = generator.generate(input);

		int numDays = input.getCalendars().size();
		int numStaffs = input.getStaffs().size();

		assertThat(output.getPlans(), hasSize(lessThanOrEqualTo(maxSolutions)));
		for (Plan plan : output.getPlans()) {
			assertThat(plan.getAllocations(), hasSize(equalTo(numDays * numStaffs)));
		}

		int solutionCount = 1;
		for (Plan plan : output.getPlans()) {
			System.out.printf("Solution %d:", solutionCount++);

			printSolution(plan);
		}
	}

	private void printSolution(Plan plan) {
		sortAllocationsByDateAndStaff(plan);
		Long idCalendar = null;
		for (Allocation allocation : plan.getAllocations()) {
			if (!allocation.getIdCalendar().equals(idCalendar)) {
				idCalendar = allocation.getIdCalendar();
				System.out.printf("\n\t%1$tY-%1$tm-%1$td: ", db.calendar(idCalendar).getDay());
			}
			System.out.printf(" %10s = %-10s ",
			                  db.staff(allocation.getIdStaff()).getName(),
			                  db.workshift(allocation.getIdWorkShift()).getName());
		}
		System.out.println("\n");
	}

	private void sortAllocationsByDateAndStaff(Plan plan) {
		Collections.sort(plan.getAllocations(), new Comparator<Allocation>() {
			public int compare(Allocation a1, Allocation a2) {
				Date d1 = db.calendar(a1.getIdCalendar()).getDay();
				Date d2 = db.calendar(a2.getIdCalendar()).getDay();
				int dateCompare = d1.compareTo(d2);
				if (dateCompare != 0) {
					return dateCompare;
				}
				String s1 = db.staff(a1.getIdStaff()).getName();
				String s2 = db.staff(a2.getIdStaff()).getName();
				return s1.compareTo(s2);
			}
		});
	}

}
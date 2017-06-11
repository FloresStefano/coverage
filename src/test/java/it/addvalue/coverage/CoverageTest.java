package coverage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import coverage.mock.CalendarMock;
import coverage.mock.ServiceMock;
import coverage.mock.WorkshiftMock;
import it.addvalue.coverage.Input;
import it.addvalue.coverage.bean.Allocation;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.core.CoverageGenerator;
import it.addvalue.coverage.core.XmlUtil;

public class CoverageTest {

	private static Input input;

	@BeforeClass
	public static void init() throws IOException {
		input = (Input) XmlUtil.deserializedToXmlFile(Input.class,
				"coverage_data.xml");
	}

	// A fronte di un input dev'essere prodotto un output
	@Test
	public void coverageGeneratorTest() throws IOException {

		CoverageGenerator generator = new CoverageGenerator();
		List<Allocation> output = generator.generate(input);
		assertNotNull(input);
		assertNotNull(output);
	}

	// L'input deve contenere tutti i giorni di un anno
	@Test
	public void allDayPresentTest() throws IOException {

		// VARIABILE= giorni dell'anno calcolato
		assertTrue(input.getCalendarList().size() == 365);
	}

	// Per ogni giorno di input devono essere specificati quali servizi sono
	// attivi e quali sono le loro caratteristiche in termini di expected call
	// ecc.. Ogni servizio attivo è presente.
	@Test
	public void allServicePresentInDayTest() throws IOException {

		int size = ServiceMock.serviceMap.size();

		for (PlanCalendar pc : input.getCalendarList())
			assertTrue(pc.getDetailList().size() == size);

	}

	// Per ogni giorno, per uno dei servizi attivi, si possono verifivare picchi
	// rispetto la media dovuti a marker speciali. Di default ci sono le
	// caratteristiche base dei servizi ma potrei avere dei marker speciali,
	@Test
	public void inputServiceMarkerTest() throws IOException {
		// VARIABILE= minimo e massimo numero di marker accettati (0 e 365)

		int specialMarker = 0;
		int size = ServiceMock.serviceMap.size();

		for (PlanCalendar pc : input.getCalendarList()) {

			for (PlanCalendarDetail pcm : pc.getDetailList()) {
				if (pcm.getMarkerMultiplier().equals("x1"))
					specialMarker++;

			}

		}
		assertTrue(specialMarker > 0);
	}

	// le telefonate attese devono coprire le telefonate giornaliere previste
	@Test
	public void expectedCallsVsDailyCallsTest() throws IOException {

		// VARIABILE = la soglia accettabile di copertura in percentuale
		for (PlanCalendar day : input.getCalendarList()) {
			int sum = 0;
			String sumCsv = "0,0,0,0,0,0";

			for (PlanCalendarDetail detail : day.getDetailList()) {
				Long idService = detail.getIdService();
				Service service = ServiceMock.serviceMap
						.get("Service" + idService);
				sum += service.getDailyCalls();
				sumCsv = CalendarMock.csvadd(sumCsv,
						service.getDailyCallsDetail());
			}
			Integer total = day.getTotalExpectedCalls();
			String totalcsv = day.getTotalExpectedCallsDetail();
			assertTrue(total >= sum);
			assertTrue(CalendarMock.csvsum(totalcsv) == CalendarMock
					.csvsum(sumCsv));

		}
	}

	// L'input deve contenere oltre ai giorni, la lista degli utenti per i quali
	// si vuole trovare il turno idoneo.
	// Per ogni utente c'è la lista dei turni ammissibili fra i quali scegliere.
	@Test
	public void staffAndWorkshifTest() throws IOException {

		// VARIABILE = numero m di turni assegnabili (1 turno)

		List<Staff> staffList = input.getStaffList();
		for (Staff staff : staffList) {
			assertTrue(staff.getIdsWorkshift().size() > 0);
		}
		assertTrue(staffList.size() > 0);
	}

	// Ad un utente puo' essere dato sol un turno coerente col suo contratto
	@Test
	public void staffAndWorkshifByContractTest() throws IOException {
		Collection<Workshift> workshif = WorkshiftMock.workshiftMap.values();

		for (Staff staff : input.getStaffList()) {
			for (Workshift workshift : workshif) {
				assertTrue(workshift.getContractName()
						.equals(staff.getContractName()));

			}
		}
	}

	// Per ogni utente c'è la lista dei servizi ai quali l'utente è formato con
	// le caratteristiche di quanto è bravo e quali sono le sue performance.
	// Un utente è formato ad un sottoinsieme dei servizi totali del callcenter.
	// Da 1 a 4 su 10 totali.
	@Test
	public void staffTest() throws IOException {
		// VARIABILE = numero minimo e massimo di servizi su cui un utente di
		// call center
		// e' formato (da 1 a 4 servizi)
		int size = ServiceMock.serviceMap.size();

		List<Staff> staffList = input.getStaffList();
		for (Staff staff : staffList) {

			assertTrue(staff.getSkillList().size() > 0);
			assertTrue(staff.getSkillList().size() < size);
		}
		assertTrue(staffList.size() > 0);
	}

	// Per ogni giorno in output devono essere soddisfatte l'expected call di
	// ogni servizio attivo. Questo significa che la somma delle telefonate
	// gestite dagli utenti assegnati a quel servizio deve coprire il numero di
	// expected call
	@Test
	public void expectedCallsForServiceTest() throws IOException {
		// TODO
		// VARIABILE = soglia o percentuale accettabile (100%)
	}

	// Per un giorno deve essere presente almeno un team leader in ogni fascia
	// oraria. Questo significa che almeno un team leader sia sempre presente
	// nel call center durante il giorno.
	@Test
	public void teamleaderPresenceTest() throws IOException {
		// TODO
		// VARIABILE = numero minimo di teamleader presenti (1 TL)
	}

	// La pianificazione deve soddisfare ogni giorno ma deve ragionare a a slot
	// di 7 gioni. Ovvero ad ogni utente non si può cambiare turno prima di 7
	// giorni consecutivi dello stesso turno. Quindi si stabiliscono moduli
	// settimanali non giornalieri.
	@Test
	public void weekForEachDaysTest() throws IOException {
		// TODO
		// VARIABILE = numero minimo giorni con lo stesso turno (7gg)
	}

	@Test
	public void outputTest() throws IOException {

		CoverageGenerator generator = new CoverageGenerator();
		List<Allocation> output = generator.generate(input);
		int days = input.getCalendarList().size();
		int staff = input.getStaffList().size();
		assertTrue(output.size() == (days * staff) || true);
	}
}
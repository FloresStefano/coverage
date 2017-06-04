package coverage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import coverage.mock.CalendarMock;
import coverage.mock.RuleMock;
import coverage.mock.ServiceMock;
import coverage.mock.StaffMock;
import it.addvalue.coverage.Input;
import it.addvalue.coverage.bean.Allocation;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.core.CoverageGenerator;
import it.addvalue.coverage.core.XmlUtil;

public class Tests {

	private static Input input;

	@BeforeClass
	public static void init() throws IOException {
		input= (Input) XmlUtil.deserializedToXmlFile(Input.class,
				"coverage_data.xml");
	}

	// A fronte di un input dev'essere prodotto un output
	@Test
	public void coverageGeneratorTest() throws IOException {

		CoverageGenerator generator = new CoverageGenerator();
		List<Allocation> output = generator.generate(input);
		assertNotNull(output);
		assertNotNull(input);
	}

	// L'input deve contenere tutti i giorni di un anno
	@Test
	public void inputDaysTest() throws IOException {

		assertTrue(input.getCalendarList().size() == 365);
	}

	// Per ogni giorno di input devono essere specificati quali servizi sono
	// attivi e quali sono le loro caratteristiche in termini di expected call
	// ecc..
	@Test
	public void inputServiceTest() throws IOException {

		for (Iterator iterator = input.getCalendarList().iterator(); iterator
				.hasNext();) {
			PlanCalendar pc = (PlanCalendar) iterator.next();
			assertTrue(
					pc.getMarkerList().size() == ServiceMock.serviceMap.size());

		}
	}
	// Per in giorno, per uno dei servizi attivi, si possono verifivare picchi
	// rispetto la media dovuti a marker speciali. Di default ci sono le
	// caratteristiche base ma potrei avere dei x1.5 o x2 o x3
	// L'input deve contenere oltre ai giorni, la lista degli utenti per i quali
	// si vuole trovare il turno idoneo.

	@Test
	public void inputServiceMarkerTest() throws IOException {
		int test = 0;
		List<PlanCalendar> calendarList = input.getCalendarList();
		for (PlanCalendar pc : calendarList) {

			List<PlanCalendarDetail> markerList = pc.getMarkerList();
			for (PlanCalendarDetail pcm : markerList) {
				if (pcm.getMarkerName().equals("special"))
					test++;

			}

		}
		assertTrue(test > 0);
	}

	// per ogni utente c'è la lista dei turni ammissibili fra i quali scegliere.
	// Per ogni utente c'è la lista dei servizi ai quali l'utente è formato con
	// le caratteristiche di quanto è bravo e quali sono le sue performance.

	// Un utente è formato ad un sottoinsieme dei servizi totali del callcenter.
	// Da 1 a 4 su 10 totali.
	@Test
	public void staffTest() throws IOException {

		List<Staff> staffList = input.getStaffList();
		for (Staff staff : staffList) {

			assertTrue(staff.getWorkshiftList().size() > 0);
			assertTrue(staff.getWorkshiftList().size() < ServiceMock.serviceMap
					.size());
		}
		assertTrue(staffList.size() > 0);
	}

	// Per ogni giorno in output devono essere soddisfatte l'expected call di
	// ogni servizio attivo. Questo significa che la somma delle telefonate
	// gestite dagli utenti assegnati a quel servizio deve coprire il numero di
	// ecpected call
	@Test
	public void testOutput() throws IOException {

	}

	// Per un giorno deve essere presente almeno un team leader per ogni fascia
	// oraria. Questo significa che almeno un team leader sia sempre presente
	// nel call center
	@Test
	public void testOutput2() throws IOException {

	}

	@Test
	public void randomDataForCoverageGeneratorTest() throws IOException {
		
		Input originalInput = new Input();
		originalInput.setCalendarList(CalendarMock.mock());
		originalInput.setRuleList(RuleMock.mock());
		originalInput.setStaffList(StaffMock.mock());

		Input deserializedInput = null;

		XmlUtil.serializedToXmlFile(originalInput, "simple_bean.xml");
		deserializedInput = (Input) XmlUtil.deserializedToXmlFile(Input.class,
				"simple_bean.xml");
		assertNotNull(deserializedInput);
	}

}
package it.addvalue.coverage;

import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.mock.repositories.Database;
import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

import static it.addvalue.coverage.core.model.RuleKeys.DEFAULT_DAILY_CALLS_HANDLED;
import static it.addvalue.coverage.core.model.RuleKeys.UNDERCOVERAGE_TOLERANCE_PERCENTAGE;
import static it.addvalue.coverage.mock.utils.CsvUtils.csvadd;

public class TestSet {

	private TestSet() {
	}

	public static void insertSimpleScenario(Database db) {
		db.rules.insert().withKey(DEFAULT_DAILY_CALLS_HANDLED).withValue("50").commit();
		db.rules.insert().withKey(UNDERCOVERAGE_TOLERANCE_PERCENTAGE).withValue("20").commit();

		Service youInvoice = db.services.insert()
		                                .withName("YouInvoice")
		                                .withCoverageFrom(540)
		                                .withCoverageTo(1080)
		                                .withDailyCallsDetail("0,0,75,75,50,0")
		                                .withDailyCallsAsSumOfDetails()
		                                .commit();
		Service youWeb = db.services.insert()
		                            .withName("YouWeb")
		                            .withCoverageFrom(840)
		                            .withCoverageTo(1080)
		                            .withDailyCallsDetail("0,0,0,50,50,0")
		                            .withDailyCallsAsSumOfDetails()
		                            .commit();
		Service youApp = db.services.insert()
		                            .withName("YouApp")
		                            .withCoverageFrom(540)
		                            .withCoverageTo(780)
		                            .withDailyCallsDetail("0,0,75,25,0,0")
		                            .withDailyCallsAsSumOfDetails()
		                            .commit();

		Workshift fullTime = db.workshifts.insert()
		                                  .withName("FullTime")
		                                  .withDailySchedule("lun", "540,780,840,1080")
		                                  .withDailySchedule("mar", "540,780,840,1080")
		                                  .withDailySchedule("mer", "540,780,840,1080")
		                                  .withDailySchedule("gio", "540,780,840,1080")
		                                  .withDailySchedule("ven", "540,780,840,1080")
		                                  .commit();
		Workshift weekends = db.workshifts.insert()
		                                  .withName("Weekends")
		                                  .withDailySchedule("sab", "540,780,840,1080")
		                                  .withDailySchedule("dom", "540,780,840,1080")
		                                  .commit();
		Workshift mornings = db.workshifts.insert()
		                                  .withName("Mornings")
		                                  .withDailySchedule("lun", "540,840")
		                                  .withDailySchedule("mar", "540,840")
		                                  .withDailySchedule("mer", "540,840")
		                                  .withDailySchedule("gio", "540,840")
		                                  .withDailySchedule("ven", "540,840")
		                                  .withDailySchedule("sab", "540,840")
		                                  .withDailySchedule("dom", "540,840")
		                                  .commit();
		Workshift afternoons = db.workshifts.insert()
		                                    .withName("Afternoons")
		                                    .withDailySchedule("lun", "780,1080")
		                                    .withDailySchedule("mar", "780,1080")
		                                    .withDailySchedule("mer", "780,1080")
		                                    .withDailySchedule("gio", "780,1080")
		                                    .withDailySchedule("ven", "780,1080")
		                                    .withDailySchedule("sab", "780,1080")
		                                    .withDailySchedule("dom", "780,1080")
		                                    .commit();

		db.staffs.insert()
		         .teamLeader()
		         .withName("AlphaTL")
		         .withIdTeam(1)
		         .withWorkshifts(fullTime, weekends, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(30).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(30).withService(youWeb).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Bravo")
		         .withIdTeam(1)
		         .withWorkshifts(fullTime, weekends, mornings)
		         .withSkills(db.skills.insert().withDailyCallsHandled(50).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(50).withService(youWeb).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Charlie")
		         .withIdTeam(1)
		         .withWorkshifts(fullTime, weekends, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(50).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(50).withService(youWeb).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Delta")
		         .withIdTeam(1)
		         .withWorkshifts(fullTime, mornings, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(40).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(40).withService(youWeb).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Echo")
		         .withIdTeam(1)
		         .withWorkshifts(weekends, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(40).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(40).withService(youWeb).commit())
		         .commit();
		db.staffs.insert()
		         .teamLeader()
		         .withName("FoxtrotTL")
		         .withIdTeam(2)
		         .withWorkshifts(fullTime, weekends, mornings)
		         .withSkills(db.skills.insert().withDailyCallsHandled(30).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(30).withService(youApp).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Golf")
		         .withIdTeam(2)
		         .withWorkshifts(fullTime, weekends, mornings)
		         .withSkills(db.skills.insert().withDailyCallsHandled(50).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(50).withService(youApp).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Hotel")
		         .withIdTeam(2)
		         .withWorkshifts(fullTime, weekends, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(50).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(50).withService(youApp).commit())
		         .commit();
		db.staffs.insert()
		         .withName("India")
		         .withIdTeam(2)
		         .withWorkshifts(fullTime, mornings, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(40).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(40).withService(youApp).commit())
		         .commit();
		db.staffs.insert()
		         .withName("Juliet")
		         .withIdTeam(2)
		         .withWorkshifts(mornings, afternoons)
		         .withSkills(db.skills.insert().withDailyCallsHandled(40).withService(youInvoice).commit(),
		                     db.skills.insert().withDailyCallsHandled(40).withService(youApp).commit())
		         .commit();

		final LocalDate startDate = new LocalDate(2017, 1, 1);
		final LocalDate endDate = new LocalDate(2017, 1, 31);
		for (LocalDate date = startDate; date.compareTo(endDate) <= 0; date = date.plusDays(1)) {
			Set<PlanCalendarDetail> details = new HashSet<PlanCalendarDetail>();
			for (Service service : db.allServices()) {
				details.add(db.calendarDetails.insert().withService(service).commit());
			}
			db.calendars.insert()
			            .withDay(date)
			            .withDetails(details)
			            .withTotalExpectedCalls(totalExpectedCallsFor(db.allServices()))
			            .withTotalExpectedCallsDetail(totalExpectedCallsDetailFor(db.allServices()))
			            .commit();
		}
	}

	private static int totalExpectedCallsFor(Set<Service> services) {
		int totalExpectedCalls = 0;
		for (Service service : services) {
			totalExpectedCalls += service.getDailyCalls();
		}
		return totalExpectedCalls;
	}

	private static String totalExpectedCallsDetailFor(Set<Service> services) {
		String totalExpectedCallsDetail = "0,0,0,0,0,0";
		for (Service service : services) {
			totalExpectedCallsDetail = csvadd(totalExpectedCallsDetail, service.getDailyCallsDetail());
		}
		return totalExpectedCallsDetail;
	}

}

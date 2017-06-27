package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static it.addvalue.coverage.mock.utils.CsvUtils.csvadd;
import static it.addvalue.coverage.mock.utils.CsvUtils.csvsum;
import static it.addvalue.coverage.mock.utils.RandomUtils.randomInRangeInclusively;
import static it.addvalue.coverage.mock.utils.RandomUtils.randomItemsIn;
import static it.addvalue.csp.collections.Collections.setOf;

@EqualsAndHashCode
public class GlobalRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int                 SERVICE_COUNT       = 6;
	private static final int                 RULE_COUNT          = 5;
	private static final int                 WORKSHIFT_COUNT     = 4;
	private static final int                 STAFF_COUNT         = 6;
	private static final int                 TEAM_SIZE           = 3;
	private static final LocalDate           START_DATE          = new LocalDate(2017, 1, 1);
	private static final LocalDate           END_DATE            = new LocalDate(2017, 1, 7);
	private final        ServiceRepository   serviceRepository   = new ServiceRepository();
	private final        RuleRepository      ruleRepository      = new RuleRepository();
	private final        WorkshiftRepository workshiftRepository = new WorkshiftRepository();
	private final        SkillRepository     skillRepository     = new SkillRepository();
	private final        StaffRepository     staffRepository     = new StaffRepository();
	private final        PlanCalendarDetailRepository calendarDetailRepository = new PlanCalendarDetailRepository();
	private final        PlanCalendarRepository       calendarRepository       = new PlanCalendarRepository();

	public Service getService(Long id) {
		return serviceRepository.data.get(id);
	}

	public Workshift getWorkshift(Long id) {
		return workshiftRepository.data.get(id);
	}

	public Staff getStaff(Long id) {
		return staffRepository.data.get(id);
	}

	public PlanCalendar getCalendar(Long id) {
		return calendarRepository.data.get(id);
	}

	public Set<PlanCalendar> allCalendars() {
		return setOf(calendarRepository.data.values());
	}

	public Set<Rule> allRules() {
		return setOf(ruleRepository.data.values());
	}

	public Set<Staff> allStaffs() {
		return setOf(staffRepository.data.values());
	}

	public Set<Workshift> allWorkshifts() {
		return setOf(workshiftRepository.data.values());
	}

	public void populate() {
		for (int i = 0; i < SERVICE_COUNT; i++) {
			String dailyCallsDetail = "50,100,100,100,100,50";
			serviceRepository.insert()
			                 .withCoverageFrom(400)
			                 .withCoverageTo(900)
			                 .withDailyCallsDetail(dailyCallsDetail)
			                 .withDailyCalls(csvsum(dailyCallsDetail))
			                 .commit();
		}

		for (int i = 0; i < RULE_COUNT; i++) {
			ruleRepository.insert().commit();
		}

		for (int i = 0; i < WORKSHIFT_COUNT; i++) {
			workshiftRepository.insert()
			                   .withDailySchedule("lun", "0,1440")
			                   .withDailySchedule("mar", "0,1440")
			                   .withDailySchedule("mer", "0,1440")
			                   .withDailySchedule("gio", "0,1440")
			                   .withDailySchedule("ven", "0,1440")
			                   .withDailySchedule("sab", "0,1440")
			                   .withDailySchedule("dom", "0,1440")
			                   .commit();
		}

		for (int i = 0; i < STAFF_COUNT; i++) {
			int numSkills = SERVICE_COUNT; //randomInRangeInclusively(1, 3);
			int numWorkshifts = WORKSHIFT_COUNT; // randomInRangeInclusively(1, 3);

			Set<Skill> skills = new HashSet<Skill>();
			for (Service service : randomServices(numSkills)) {
				skills.add(skillRepository.insert()
				                          .withHandledCallsOverridden(randomInRangeInclusively(40, 50))
				                          .withSkillLevel(randomInRangeInclusively(4, 10))
				                          .withUsagePriority(randomInRangeInclusively(0, 10))
				                          .withService(service)
				                          .commit());
			}

			boolean teamLeader = true; // i % TEAM_SIZE == 0;
			staffRepository.insert()
			               .withIdTeam((long) i / TEAM_SIZE).withTeamLeader(teamLeader)
			               .withWorkshifts(randomWorkshifts(numWorkshifts))
			               .withValidFrom(START_DATE)
			               .withvalidTo(END_DATE)
			               .withSkills(skills)
			               .commit();
		}

		Set<Service> services = allServices();
		for (LocalDate date = START_DATE; date.compareTo(END_DATE) <= 0; date = date.plusDays(1)) {
			Set<PlanCalendarDetail> details = new HashSet<PlanCalendarDetail>();
			for (Service service : services) {
				details.add(calendarDetailRepository.insert().withService(service).commit());
			}
			calendarRepository.insert()
			                  .withDay(date)
			                  .withDetails(details)
			                  .withTotalExpectedCalls(totalExpectedCallsFor(services))
			                  .withTotalExpectedCallsDetail(totalExpectedCallsDetailFor(services))
			                  .commit();
		}
	}

	private Set<Service> randomServices(int count) {
		return randomItemsIn(serviceRepository.data.values(), count);
	}

	private Set<Workshift> randomWorkshifts(int count) {
		return randomItemsIn(workshiftRepository.data.values(), count);
	}

	public Set<Service> allServices() {
		return setOf(serviceRepository.data.values());
	}

	private int totalExpectedCallsFor(Set<Service> services) {
		int totalExpectedCalls = 0;
		for (Service service : services) {
			totalExpectedCalls += service.getDailyCalls();
		}
		return totalExpectedCalls;
	}

	private String totalExpectedCallsDetailFor(Set<Service> services) {
		String totalExpectedCallsDetail = "0,0,0,0,0,0";
		for (Service service : services) {
			totalExpectedCallsDetail = csvadd(totalExpectedCallsDetail, service.getDailyCallsDetail());
		}
		return totalExpectedCallsDetail;
	}

}

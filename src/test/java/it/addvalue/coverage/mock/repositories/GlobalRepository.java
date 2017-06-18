package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static it.addvalue.coverage.mock.utils.RandomUtils.randomInRange;
import static it.addvalue.coverage.mock.utils.RandomUtils.randomItemsIn;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GlobalRepository {

	private static final int                          SERVICE_COUNT            = 6;
	private static final int                          RULE_COUNT               = 5;
	private static final int                          WORKSHIFT_COUNT          = 4;
	private static final int                          STAFF_COUNT              = 15;
	private static final int                          CALENDAR_COUNT           = 365;
	private final        ServiceRepository            serviceRepository        = new ServiceRepository();
	private final        RuleRepository               ruleRepository           = new RuleRepository();
	private final        WorkshiftRepository          workshiftRepository      = new WorkshiftRepository();
	private final        SkillRepository              skillRepository          = new SkillRepository();
	private final        StaffRepository              staffRepository          = new StaffRepository();
	private final        PlanCalendarDetailRepository calendarDetailRepository = new PlanCalendarDetailRepository();
	private final        PlanCalendarRepository       calendarRepository       = new PlanCalendarRepository();

	public GlobalRepository() {
		for (int i = 0; i < SERVICE_COUNT; i++) {
			serviceRepository.newItem();
		}
		for (int i = 0; i < RULE_COUNT; i++) {
			ruleRepository.newItem();
		}
		for (int i = 0; i < WORKSHIFT_COUNT; i++) {
			workshiftRepository.newItem();
		}
		for (int i = 0; i < STAFF_COUNT; i++) {
			int numSkills = randomInRange(1, 3);
			int numWorkshifts = randomInRange(1, 3);
			Set<Service> services = randomServices(numSkills);
			Set<Skill> skills = new HashSet<Skill>();
			for (Service service : services) {
				skills.add(skillRepository.newItem(service));
			}
			Set<Workshift> workshifts = randomWorkshifts(numWorkshifts);
			staffRepository.newItem(skills, workshifts);
		}
		Set<Service> services = allServices();
		for (int i = 0; i < CALENDAR_COUNT; i++) {
			Set<PlanCalendarDetail> details = new HashSet<PlanCalendarDetail>();
			for (Service service : services) {
				details.add(calendarDetailRepository.newItem(service));
			}
			calendarRepository.newItem(2017, i, details, services);
		}
	}

	private Set<Service> randomServices(int count) {
		return randomItemsIn(serviceRepository.data.values(), count);
	}

	private Set<Workshift> randomWorkshifts(int count) {
		return randomItemsIn(workshiftRepository.data.values(), count);
	}

	public Set<Service> allServices() {
		return new HashSet<Service>(serviceRepository.data.values());
	}

	public Service getService(Long id) {
		return serviceRepository.data.get(id);
	}

	public Set<PlanCalendar> allCalendars() {
		return new HashSet<PlanCalendar>(calendarRepository.data.values());
	}

	public Set<Rule> allRules() {
		return new HashSet<Rule>(ruleRepository.data.values());
	}

	public Set<Staff> allStaffs() {
		return new HashSet<Staff>(staffRepository.data.values());
	}

	public Set<Workshift> allWorkshifts() {
		return new HashSet<Workshift>(workshiftRepository.data.values());
	}

	@Test
	public void testXmlSerializability()
	throws IOException {
		assertThat(XmlUtil.prettyPrint(serviceRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(ruleRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(workshiftRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(skillRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(staffRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(calendarDetailRepository.data), is(notNullValue()));
		assertThat(XmlUtil.prettyPrint(calendarRepository.data), is(notNullValue()));
	}

}

package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static it.addvalue.coverage.mock.utils.RandomUtils.randomInRange;
import static it.addvalue.coverage.mock.utils.RandomUtils.randomItemsIn;
import static it.addvalue.csp.collections.Collections.setOf;

@EqualsAndHashCode
public class GlobalRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int                          SERVICE_COUNT            = 6;
	private static final int                          RULE_COUNT               = 5;
	private static final int                          WORKSHIFT_COUNT          = 4;
	private static final int                          STAFF_COUNT              = 15;
	private static final LocalDate                    START_DATE               = new LocalDate(2017, 1, 1);
	private static final LocalDate                    END_DATE                 = new LocalDate(2017, 12, 31);
	private final        ServiceRepository            serviceRepository        = new ServiceRepository();
	private final        RuleRepository               ruleRepository           = new RuleRepository();
	private final        WorkshiftRepository          workshiftRepository      = new WorkshiftRepository();
	private final        SkillRepository              skillRepository          = new SkillRepository();
	private final        StaffRepository              staffRepository          = new StaffRepository();
	private final        PlanCalendarDetailRepository calendarDetailRepository = new PlanCalendarDetailRepository();
	private final        PlanCalendarRepository       calendarRepository       = new PlanCalendarRepository();

	public Service getService(Long id) {
		return serviceRepository.data.get(id);
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
			serviceRepository.newItem();
		}
		for (int i = 0; i < RULE_COUNT; i++) {
			ruleRepository.newItem();
		}
		for (int i = 0; i < WORKSHIFT_COUNT; i++) {
			workshiftRepository.newItem();
		}
		for (int i = 0; i < STAFF_COUNT; i++) {
			val numSkills = randomInRange(1, 3);
			val numWorkshifts = randomInRange(1, 3);
			val services = randomServices(numSkills);
			val skills = new HashSet<Skill>();
			for (val service : services) {
				skills.add(skillRepository.newItem(service));
			}
			val workshifts = randomWorkshifts(numWorkshifts);
			staffRepository.newItem(skills, workshifts);
		}
		val services = allServices();
		for (LocalDate date = START_DATE; date.compareTo(END_DATE) <= 0; date = date.plusDays(1)) {
			val details = new HashSet<PlanCalendarDetail>();
			for (val service : services) {
				details.add(calendarDetailRepository.newItem(service));
			}
			calendarRepository.newItem(date, details, services);
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

}

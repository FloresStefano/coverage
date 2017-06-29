package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Input;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

import static it.addvalue.csp.collections.Collections.setOf;

@EqualsAndHashCode
public class Database implements Serializable {

	public final ServiceRepository            services        = new ServiceRepository();
	public final RuleRepository               rules           = new RuleRepository();
	public final WorkshiftRepository          workshifts      = new WorkshiftRepository();
	public final SkillRepository              skills          = new SkillRepository();
	public final StaffRepository              staffs          = new StaffRepository();
	public final PlanCalendarDetailRepository calendarDetails = new PlanCalendarDetailRepository();
	public final PlanCalendarRepository       calendars       = new PlanCalendarRepository();

	private static final long serialVersionUID = 1L;

	public Service service(Long id) {
		return services.data.get(id);
	}

	public Workshift workshift(Long id) {
		return workshifts.data.get(id);
	}

	public Staff staff(Long id) {
		return staffs.data.get(id);
	}

	public PlanCalendar calendar(Long id) {
		return calendars.data.get(id);
	}

	public Input toInput() {
		Input input = new Input();
		input.getCalendars().addAll(calendars.data.values());
		input.getRules().addAll(rules.data.values());
		input.getStaffs().addAll(staffs.data.values());
		input.getServices().addAll(services.data.values());
		input.getWorkshifts().addAll(workshifts.data.values());
		return input;
	}

	public Set<PlanCalendar> allCalendars() {
		return setOf(calendars.data.values());
	}

	public Set<Rule> allRules() {
		return setOf(rules.data.values());
	}

	public Set<Staff> allStaffs() {
		return setOf(staffs.data.values());
	}

	public Set<Service> allServices() {
		return setOf(services.data.values());
	}

	public Set<Workshift> allWorkshifts() {
		return setOf(workshifts.data.values());
	}

}

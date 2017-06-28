package it.addvalue.coverage.core.model;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "staff")
public class PlanStaff {

	private static final int DEFAULT_SKILL_LEVEL    = 3;
	private static final int DEFAULT_USAGE_PRIORITY = 3;

	private final Staff staff;
	private final Map<Service, Performance> performances = new HashMap<Service, Performance>();

	public boolean canHandle(Service service) {
		Performance performance = performances.get(service);
		return performance != null && performance.getUsagePriority() > 0;
	}

	public int usagePriorityFor(Service service) {
		return performances.get(service).getUsagePriority();
	}

	public int skillLevelFor(Service service) {
		return performances.get(service).getSkillLevel();
	}

	public int hourlyCallsFor(Service service) {
		return performances.get(service).getHourlyCalls();
	}

	public boolean isPresentOn(PlanDay day) {
		return (staff.getValidFrom() == null || day.afterInclusively(staff.getValidFrom())) &&
		       (staff.getValidTo() == null || day.beforeInclusively(staff.getValidTo()));
	}

	public boolean isTeamLeader() {
		return BooleanUtils.isTrue(staff.getTeamLeader());
	}

	public String toString() {
		return staff.getName();
	}

	@Data
	public static class Performance {

		private final Skill skill;
		private final int   dailyCalls;
		private final int   hourlyCalls;
		private final int   skillLevel;
		private final int   usagePriority;

		public Performance(Skill skill, int defaultDailyCallsHandled) {
			this.skill = skill;
			dailyCalls = ObjectUtils.defaultIfNull(skill.getHandledCallsOverridden(), defaultDailyCallsHandled);
			hourlyCalls = (int) Math.round(dailyCalls / 8.0);
			skillLevel = ObjectUtils.defaultIfNull(skill.getSkillLevel(), DEFAULT_SKILL_LEVEL);
			usagePriority = ObjectUtils.defaultIfNull(skill.getUsagePriority(), DEFAULT_USAGE_PRIORITY);
		}

	}

}

package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "staff")
public class PlanStaff {

	private static final int DEFAULT_DAILY_CALLS_HANDLED = 50;

	private final Staff staff;
	private final Map<Service, Performance> performances = new HashMap<Service, Performance>();

	public boolean canHandle(Service service) {
		return performances.containsKey(service);
	}

	public int hourlyCallsFor(Service service) {
		return performances.get(service).getHourlyCalls();
	}

	@Data
	public static class Performance {

		private final Skill skill;
		private final int   dailyCalls;
		private final int   hourlyCalls;

		public Performance(Skill skill) {
			this.skill = skill;
			dailyCalls = ObjectUtils.defaultIfNull(skill.getHandledCallsOverridden(), DEFAULT_DAILY_CALLS_HANDLED);
			hourlyCalls = dailyCalls / 8;
		}

	}

}

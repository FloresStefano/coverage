package it.addvalue.coverage.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(of = {"weekyear", "weekOfWeekyear"})
public class PlanWeek implements Comparable<PlanWeek> {

	private final int weekyear;
	private final int weekOfWeekyear;
	private final Set<PlanDay>      days              = new HashSet<PlanDay>();
	private final Set<PlanVariable> involvedVariables = new HashSet<PlanVariable>();

	public String toString() {
		return String.format("%d-%d", weekyear, weekOfWeekyear);
	}

	public int compareTo(PlanWeek that) {
		int diff = this.weekyear - that.weekyear;
		if (diff != 0) {
			return diff;
		}
		return this.weekOfWeekyear - that.weekOfWeekyear;
	}

}

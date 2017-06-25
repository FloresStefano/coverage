package it.addvalue.coverage.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(of = {"weekyear", "weekOfWeekyear"})
@ToString(of = {"weekyear", "weekOfWeekyear"})
public class PlanWeek {

	private final int weekyear;
	private final int weekOfWeekyear;
	private final Set<PlanDay>      days              = new HashSet<PlanDay>();
	private final Set<PlanVariable> involvedVariables = new HashSet<PlanVariable>();

}

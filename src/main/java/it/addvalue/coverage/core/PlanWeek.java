package it.addvalue.coverage.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(of = {"weekyear", "weekOfWeekyear"})
public class PlanWeek {

	private final int weekyear;
	private final int weekOfWeekyear;
	private final Set<PlanDay> days = new HashSet<PlanDay>();

}

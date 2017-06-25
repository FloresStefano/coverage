package it.addvalue.coverage.core.constraints;

import java.util.BitSet;

public class DailyCoverageMerger {

	private static final int DAY_MINUTES = 24 * 60;

	private final BitSet coverage = new BitSet(DAY_MINUTES);

	public void add(int[] dailySchedule) {
		for (int i = 0; i < dailySchedule.length; i += 2) {
			int startMinutes = dailySchedule[i];
			int endMinutes = dailySchedule[i + 1];

			coverage.set(startMinutes, endMinutes + 1);
		}
	}

	public boolean covers(int coverageFrom, int coverageTo) {
		return coverage.nextClearBit(coverageFrom) > coverageTo;
	}

}

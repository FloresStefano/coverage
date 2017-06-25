package it.addvalue.coverage.core.constraints;

import lombok.Getter;

public class DailyCallsAccumulator {

	private static final int   NUM_SLOTS             = 6;
	private static final int   SLOT_DURATION_HOURS   = 4;
	private static final int   SLOT_DURATION_MINUTES = 240;
	private static final int[] SLOT_START_MINUTES    = {0, 240, 480, 720, 960, 1200};
	private static final int[] SLOT_END_MINUTES      = {240, 480, 720, 960, 1200, 1440};

	private final int[] callsInSlot = {0, 0, 0, 0, 0, 0};
	@Getter
	private       int   totalCalls  = 0;

	public void add(int[] dailySchedule, int maxCallsPerHour) {
		int maxCallsPerSlot = maxCallsPerHour * SLOT_DURATION_HOURS;

		for (int i = 0; i < dailySchedule.length; i += 2) {
			int startMinutes = dailySchedule[i];
			int endMinutes = dailySchedule[i + 1];

			int startSlot = slot(startMinutes);
			int endSlot = slot(endMinutes);

			for (int slot = startSlot; slot <= endSlot; slot++) {
				int numCallsInSlot = numCallsInSlot(maxCallsPerSlot, slot, startMinutes, endMinutes);
				totalCalls += numCallsInSlot;
				callsInSlot[slot] += numCallsInSlot;
			}
		}
	}

	private static int slot(int minutes) {
		for (int i = 0; i < NUM_SLOTS; i++) {
			if (SLOT_START_MINUTES[i] <= minutes && SLOT_END_MINUTES[i] >= minutes) {
				return i;
			}
		}
		throw new IllegalArgumentException("Slot not found for minutes: " + minutes);
	}

	private static int numCallsInSlot(int maxCallsPerSlot, int slot, int startMinutes, int endMinutes) {
		startMinutes = Math.max(SLOT_START_MINUTES[slot], startMinutes);
		endMinutes = Math.min(SLOT_END_MINUTES[slot], endMinutes);
		return maxCallsPerSlot * (endMinutes - startMinutes) / SLOT_DURATION_MINUTES;
	}

	public boolean covers(int[] serviceDailyCallsDetail) {
		for (int i = 0; i < NUM_SLOTS; i++) {
			if (callsInSlot[i] < serviceDailyCallsDetail[i]) {
				return false;
			}
		}
		return true;
	}

}

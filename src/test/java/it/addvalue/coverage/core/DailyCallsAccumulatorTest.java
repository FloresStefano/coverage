package it.addvalue.coverage.core;

import org.junit.Test;

import static it.addvalue.coverage.utils.CsvUtils.toIntArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DailyCallsAccumulatorTest {

	@Test
	public void test() {
		int[] dailySchedule = toIntArray("570,810,870,1110"); // 9.30-13.30 -- 14.30-18.30
		int maxCallsPerHour = 10;

		DailyCallsAccumulator dailyCallsAccumulator = new DailyCallsAccumulator();
		dailyCallsAccumulator.add(dailySchedule, maxCallsPerHour);

		assertThat(dailyCallsAccumulator.covers(toIntArray("0,0,25,30,25,0")), is(equalTo(true)));
		assertThat(dailyCallsAccumulator.covers(toIntArray("0,0,30,35,30,0")), is(equalTo(false)));
	}

}

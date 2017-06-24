package it.addvalue.coverage.core;

import org.junit.Test;

import static it.addvalue.coverage.utils.CsvUtils.toIntArray;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DailyCoverageMergerTest {

	@Test
	public void test() {
		int[] dailySchedule = toIntArray("570,810,870,1110"); // 9.30-13.30 -- 14.30-18.30

		DailyCoverageMerger dailyCoverageMerger = new DailyCoverageMerger();

		dailyCoverageMerger.add(dailySchedule);

		assertThat(dailyCoverageMerger.covers(600, 800), is(equalTo(true)));
		assertThat(dailyCoverageMerger.covers(570, 810), is(equalTo(true)));
		assertThat(dailyCoverageMerger.covers(820, 860), is(equalTo(false)));
		assertThat(dailyCoverageMerger.covers(400, 900), is(equalTo(false)));

		dailyCoverageMerger.add(toIntArray("400,570,810,900"));

		assertThat(dailyCoverageMerger.covers(820, 860), is(equalTo(true)));
		assertThat(dailyCoverageMerger.covers(400, 1110), is(equalTo(true)));
		assertThat(dailyCoverageMerger.covers(400, 1440), is(equalTo(false)));

		dailyCoverageMerger.add(toIntArray("1000,1440"));

		assertThat(dailyCoverageMerger.covers(400, 1440), is(equalTo(true)));
	}

}

package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.Calendar;

public class CalendarMock
{

    private static final int CALENDAR_COUNT = 365;

    public static List<Calendar> mock()
    {
        List<Calendar> list = new ArrayList<Calendar>();

        for ( long i = 0; i < CALENDAR_COUNT; i++ )
        {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.set(java.util.Calendar.DAY_OF_YEAR, (int) i); // 0-based
            Calendar e = new Calendar();
            e.setName("name" + i);
            e.setId(i);
            e.setDay(c.getTime());
            e.setWeekOfYear(c.get(java.util.Calendar.WEEK_OF_YEAR));
            list.add(e);
        }
        return list;
    }

}

package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.DailySchedule;

public class DailyScheduleMock
{

    private static final int DAILYSCHEDULE_COUNT = 10;

    public static List<DailySchedule> mock()
    {
        List<DailySchedule> list = new ArrayList<DailySchedule>();
        for ( long i = 0; i < DAILYSCHEDULE_COUNT; i++ )
        {
            DailySchedule e = new DailySchedule();
            e.setId(i);
            e.setName("schedule"+i);
            list.add(e);
        }
        return list;
    }

}

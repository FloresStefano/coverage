package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.Workshift;

public class WorkshiftMock
{

    private static final int WORKSHIFT_COUNT = 3;

    public static List<Workshift> mock()
    {
        List<Workshift> list = new ArrayList<Workshift>();
        for ( long i = 0; i < WORKSHIFT_COUNT; i++ )
        {
            Workshift e = new Workshift();
            e.setId(i);
            e.setName("workshift"+i);
            e.setDailyScheduleList(DailyScheduleMock.mock());
            list.add(e);
        }
        return list;
    }

}

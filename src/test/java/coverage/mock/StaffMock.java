package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.Staff;

public class StaffMock
{


    private static final int STAFF_COUNT     = 70;
    
    
    public static List<Staff> mock()
    {
        List<Staff> list = new ArrayList<Staff>();
        for ( long i = 0; i < STAFF_COUNT; i++ )
        {
            Staff e = new Staff();
            e.setId(i);
            e.setName("name"+i);
            e.setServiceList(ServiceMock.mock());
            e.setWorkshiftList(WorkshiftMock.mock());
            list.add(e);
        }
        return list;
    }
}

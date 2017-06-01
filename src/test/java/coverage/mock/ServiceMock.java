package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.Service;

public class ServiceMock
{

    private static final int SERVICE_COUNT = 3;

    public static List<Service> mock()
    {
        List<Service> list = new ArrayList<Service>();
        for ( long i = 0; i < SERVICE_COUNT; i++ )
        {
            Service e = new Service();
            e.setId(i);
            e.setName("service"+i);
            e.setStaffSkill(StaffSkillMock.mock());
            list.add(e);
        }
        return list;
    }
}

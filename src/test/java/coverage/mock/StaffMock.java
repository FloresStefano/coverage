package coverage.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;

public class StaffMock
{


    private static final int STAFF_COUNT     = 40;
    
    
    public static List<Staff> mock()
    {
        List<Staff> list = new ArrayList<Staff>();
        for ( long i = 0; i < STAFF_COUNT; i++ )
        {
            Staff e = new Staff();
            e.setId(i);
            e.setName("staff_"+i);
            e.setContractName(WorkshiftMock.CONTRACTNAME);
            e.setIdTeam(0L);
            e.setServiceList(ServiceMock.mock());
            List<Workshift> workshift = WorkshiftMock.mock();
			e.setWorkshiftList(workshift);
            list.add(e);
        }
        return list;
    }
    
    @Test
    public void testmock() throws IOException
    {
        XmlMapper xmlMapper = new XmlMapper();
        // xmlMapper.writeValue(new File("mock.xml"), mock());
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mock()));
    }
}

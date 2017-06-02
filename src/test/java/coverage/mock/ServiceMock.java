package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.addvalue.coverage.bean.Service;

public class ServiceMock
{

    private static Map<Integer, Service> serviceMap;
    static
    {
        serviceMap = new HashMap<Integer, Service>();
        serviceMap.put(0, mockOne(0L));
        serviceMap.put(1, mockOne(1L));
        serviceMap.put(2, mockOne(2L));
        serviceMap.put(3, mockOne(3L));
        serviceMap.put(4, mockOne(4L));
        serviceMap.put(5, mockOne(5L));
    }


    public static List<Service> mock()
    {
        List<Service> list = new ArrayList<Service>();
        for ( long i = 0; i < random(1,3); i++ )
        {
            Random generator = new Random();
            Object[] values = serviceMap.values().toArray();
            Service randomValue = (Service) values[generator.nextInt(values.length)];
            list.add(randomValue);
        }
        return list;
    }

    private static Service mockOne(long l)
    {
        Service e = new Service();
        e.setId(l);
        e.setName("service" + l);
        e.setCoverageFrom(400);
        e.setCoverageTo(900);
        e.setDailyCalls(1000);
        e.setDailyCallsDetail("200,400,400,400,400,200");
        e.setDailyCallsDetailTemplate("base");
        e.setHandledCallsOverrided(random(40, 50));
        e.setSkillLevel(random(4, 10));
        e.setUsagePriority(10);
        return e;
    }

    private static int random(int min,
                              int max)
    {

        if ( min >= max )
        {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Test
    public void testmock() throws IOException
    {
        XmlMapper xmlMapper = new XmlMapper();
        String writeValueAsString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mock());
		System.out.println(writeValueAsString);
		assertNotNull(writeValueAsString);
    }
}

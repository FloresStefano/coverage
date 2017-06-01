package coverage.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.addvalue.coverage.bean.Marker;
import it.addvalue.coverage.bean.PlanCalendar;

public class CalendarMock
{

    private static final int CALENDAR_COUNT = 365;

    public static List<PlanCalendar> mock()
    {
        List<PlanCalendar> list = new ArrayList<PlanCalendar>();

        for ( long i = 1; i < CALENDAR_COUNT; i++ )
        {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DAY_OF_YEAR, (int) i); // 0-based
            c.set(Calendar.YEAR, 2017);
            PlanCalendar e = new PlanCalendar();
            String displayName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ITALIAN);
            e.setId(i);
            e.setName(displayName);
            e.setDay(c.getTime());
            e.setWeekOfYear(c.get(Calendar.WEEK_OF_YEAR));
            e.setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
            e.setExpectedCalls(1000);
            e.setExpectedCallsDetail("100,200,200,200,200,100");
            if ( i > 350 && i < 360 )
            {
                ArrayList<Marker> markerList = new ArrayList<Marker>();
                Marker m = new Marker();
                m.setDailyCallsMarked(2000);
                m.setDailyCallsDetailMarked("200,400,400,400,400,200");
                m.setId(i);
                m.setIdService(1L);
                m.setValue("x2");
                markerList.add(m);
                e.setMarkerList(markerList);
            }
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

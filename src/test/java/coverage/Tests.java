package coverage;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import coverage.mock.CalendarMock;
import coverage.mock.RuleMock;
import coverage.mock.StaffMock;
import it.addvalue.coverage.Input;
import it.addvalue.coverage.bean.Staff;

public class Tests
{

    @Test
    public void firstTest() throws IOException
    {

        Input input = new Input();
        input.setCalendarList(CalendarMock.mock());
        input.setRuleList(RuleMock.mock());
        input.setStaffList(StaffMock.mock());

        XmlMapper xmlMapper = new XmlMapper();
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
        assertTrue(input != null);
    }

    @Test
    public void whenJavaSerializedToXmlFile_thenCorrect() throws IOException
    {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("simple_bean.xml"), StaffMock.mock());
    }
    
    @Test
    public void whenJavaGotFromXmlStr_thenCorrect() throws IOException {
        File file = new File("simple_bean.xml");
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(file));
        List<Staff> value = xmlMapper.readValue(xml, List.class);
    }
    
    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
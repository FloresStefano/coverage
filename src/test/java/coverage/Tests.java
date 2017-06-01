package coverage;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import coverage.mock.CalendarMock;
import coverage.mock.RuleMock;
import coverage.mock.StaffMock;
import it.addvalue.coverage.Input;

public class Tests
{

    @Test
    public void firstTest()
    {

        Input input = new Input();
        input.setCalendarList(CalendarMock.mock());
        input.setRuleList(RuleMock.mock());
        input.setStaffList(StaffMock.mock());

        assertTrue(input != null);
    }








}
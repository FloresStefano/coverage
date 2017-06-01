package coverage.mock;

import it.addvalue.coverage.bean.StaffSkill;

public class StaffSkillMock
{

    
    
    public static StaffSkill mock()
    {

            StaffSkill e = new StaffSkill();
            e.setId(0L);
            e.setName("name"+0);

        return e;
    }
}

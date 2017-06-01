package coverage.mock;

import java.util.ArrayList;
import java.util.List;

import it.addvalue.coverage.bean.Rule;

public class RuleMock
{


    private static final int RULE_COUNT     = 10;
    
    
    public static List<Rule> mock()
    {
        List<Rule> list = new ArrayList<Rule>();
        for ( long i = 0; i < RULE_COUNT; i++ )
        {
            Rule e = new Rule();
            e.setName("name"+i);
            e.setId(i);
           
            list.add(e);
        }
        return list;
    }
}

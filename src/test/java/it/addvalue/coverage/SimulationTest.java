package it.addvalue.coverage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import it.addvalue.coverage.bean.Input;
import it.addvalue.coverage.bean.Output;
import it.addvalue.coverage.bean.Plan;
import it.addvalue.coverage.core.CoverageGenerator;
import it.addvalue.coverage.mock.utils.JsonUtils;

public class SimulationTest
{

    private static Input input;

    @BeforeClass
    public static void setupData()
    {
        //input = JsonUtils.deserialize("input-mock.json", Input.class);
        //input = JsonUtils.deserialize("input-real1.json", Input.class);
        input = JsonUtils.deserialize("input-real2.json", Input.class);
        JsonUtils.prettyPrint(input);
    }

    @Test
    public void outputTest()
    {
        CoverageGenerator generator = new CoverageGenerator();

        int maxSolutions = 4;

        generator.setMaxSolutions(maxSolutions);
        generator.setMaxIterations(200L);
        generator.setFullSearch(true);

        assertThat(input, is(notNullValue()));

        Output output = generator.generate(input);

        assertThat(output, is(notNullValue()));

        int numDays = input.getCalendars().size();
        int numStaffs = input.getStaffs().size();

        assertThat(output.getPlans(), hasSize(lessThanOrEqualTo(maxSolutions)));
        for ( Plan plan : output.getPlans() )
        {
            assertThat(plan.getAllocations(), hasSize(lessThanOrEqualTo(numDays * numStaffs)));
        }

        int solutionCount = 1;
        for ( Plan plan : output.getPlans() )
        {
            System.out.printf("Solution %d:\n", solutionCount++);
            JsonUtils.print(plan);
        }
    }
    
    

}
package it.addvalue.csp.engine;

import org.junit.Test;

import static it.addvalue.csp.engine.CspSolverTestUtils.are;
import static it.addvalue.csp.engine.CspSolverTestUtils.have;
import static it.addvalue.csp.engine.CspSolverTestUtils.orderedByLooselyAscendingValuesOf;
import static it.addvalue.csp.engine.CspSolverTestUtils.subsetOf;
import static it.addvalue.csp.engine.CspSolverTestUtils.theEmptySet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class CspSolverTest extends CspSolverTestBase {

	@Test
	public void aFeasibleProblemHasSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible()), are(equalTo(theExpectedSolutions())));
	}

	@Test
	public void theSolutionsOfAFeasibleProblemAreAdmissible() {
		assertThat(theSolutionsOf(aProblem().feasible()), satisfyTheConstraintsOf(theProblem()));
	}

	@Test
	public void anInfeasibleProblemHasNoSolutions() {
		assertThat(theSolutionsOf(aProblem().infeasible()), are(theEmptySet()));
	}

	@Test
	public void aWeightedSearchProducesSolutionsOrderedByAscendingCost() {
		assertThat(theSolutionsOf(aProblem().feasible().with(costFunction())),
		           are(orderedByLooselyAscendingValuesOf(theCostFunction())));
	}

	@Test
	public void aBoundedSearchProducesAtMostNSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible().withBounded(maxSolutions())),
		           hasSize(lessThanOrEqualTo(maxSolutions())));
	}

	@Test
	public void aFullBoundedWeightedSearchProducesTheCheapestSolutionsOrderedByAscendingCost() {
		assertThat(theSolutionsOf(aProblem().feasible()
		                                    .withFullSearch()
		                                    .withBounded(maxSolutions())
		                                    .with(costFunction())), have(cost(theLowestCostsInOrder())));
	}

	@Test
	public void aBoundedSearchProducesASubsetOfTheUnboundedSearchSolutions() {
		assertThat(theSolutionsOf(aProblem().feasible().withBounded(maxSolutions())),
		           are(subsetOf(theSolutionsOf(theProblem().withUnboundedMaxSolutions()))));
	}

}

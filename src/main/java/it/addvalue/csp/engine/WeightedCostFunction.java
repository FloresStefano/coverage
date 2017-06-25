package it.addvalue.csp.engine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeightedCostFunction implements CostFunction {

	private final int          weight;
	private final CostFunction delegate;

	public int evaluate(Solution solution) {
		return weight * delegate.evaluate(solution);
	}

}

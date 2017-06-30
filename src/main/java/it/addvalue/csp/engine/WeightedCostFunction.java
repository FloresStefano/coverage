package it.addvalue.csp.engine;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WeightedCostFunction implements CostFunction {

	private final int          weight;
	private final CostFunction delegate;

	public int evaluate(Solution solution) {
		return weight * delegate.evaluate(solution);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(weight).append(" * ").append(delegate);
		return sb.toString();
	}

}

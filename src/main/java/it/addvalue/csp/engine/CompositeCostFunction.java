package it.addvalue.csp.engine;

import java.util.ArrayList;
import java.util.List;

public class CompositeCostFunction implements CostFunction {

	private final List<CostFunction> delegates = new ArrayList<CostFunction>();

	public void addDelegate(CostFunction delegate) {
		delegates.add(delegate);
	}

	public int evaluate(Solution solution) {
		int sum = 0;
		for (CostFunction delegate : delegates) {
			sum += delegate.evaluate(solution);
		}
		return sum;
	}

}

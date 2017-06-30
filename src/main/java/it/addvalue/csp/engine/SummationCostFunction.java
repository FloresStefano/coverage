package it.addvalue.csp.engine;

import java.util.ArrayList;
import java.util.List;

public class SummationCostFunction implements CostFunction {

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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (CostFunction delegate : delegates) {
			if (sb.length() > 0) {
				sb.append(" + ");
			}
			sb.append(delegate);
		}
		return sb.toString();
	}

}

package it.addvalue.csp.engine;

import java.util.Set;

public interface Constraint {
	Set<? extends Variable> variables();
	boolean verify(Solution solution);
}

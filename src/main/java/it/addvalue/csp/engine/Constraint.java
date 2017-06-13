package it.addvalue.csp.engine;

import java.util.Set;

public interface Constraint {

	Set<Variable> variables();
	boolean verify(Solution solution);

}

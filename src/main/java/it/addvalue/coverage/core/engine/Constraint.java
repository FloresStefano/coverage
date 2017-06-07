package it.addvalue.coverage.core.engine;

import java.util.Set;

public interface Constraint {

	Set<Variable> variables();
	boolean verify(Solution solution);

}

package it.addvalue.csp.engine;

import lombok.extern.log4j.Log4j;

import java.util.Set;

@Log4j(topic = "csp")
public class Trace {

	public static boolean traceBeginSolve         = true;
	public static boolean traceConstraintViolated = false;
	public static boolean traceSolutionFound      = true;
	public static boolean traceEndSolve           = true;

	public static void beginSolve(Csp csp) {
		if (traceBeginSolve) {
			logf("Resolution started\n%s", csp);
		}
	}

	private static void logf(String format, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format(format, args));
		}
	}

	public static void constraintViolated(Constraint constraint, Solution solution) {
		if (traceConstraintViolated) {
			logf("Constraint violated: %s", constraint);
		}
	}

	public static void solutionFound(Solution solution) {
		if (traceSolutionFound) {
			logf("Found solution: %s", solution);
		}
	}

	public static void endSolve(Set<Solution> solutions) {
		if (traceEndSolve) {
			logf("Resolution ended: %d solutions found", solutions.size());
		}
	}

	private static void log(Object message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

}

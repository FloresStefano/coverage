package it.addvalue.csp.engine;

import lombok.extern.log4j.Log4j;

import java.util.Set;

@Log4j(topic = "csp")
public class Trace {

	private static boolean traceBeginSolve         = true;
	private static boolean traceConstraintViolated = false;
	private static boolean traceSolutionFound      = true;
	private static boolean traceEndSolve           = true;

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
			log("Resolution ended: " + solutions.size() + " solutions found");
		}
	}

	private static void log(Object message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

}

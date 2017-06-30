package it.addvalue.csp.engine;

import lombok.extern.log4j.Log4j;

import java.util.Set;

@Log4j(topic = "csp")
public class Trace {

	public static boolean enabled              = true;
	public static boolean beginSolve           = true;
	public static boolean constraintViolated   = false;
	public static boolean solutionFound        = false;
	public static boolean endSolve             = true;
	public static boolean maxSolutionsReached  = true;
	public static boolean maxIterationsReached = true;

	public static void beginSolve(Csp csp) {
		if (enabled && beginSolve) {
			logf("Resolution started\n%s", csp);
		}
	}

	private static void logf(String format, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format(format, args));
		}
	}

	public static void maxIterationsReached(Csp csp) {
		if (enabled && maxIterationsReached) {
			log("Max-iterations limit reached");
		}
	}

	private static void log(Object message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public static void maxSolutionsReached(Csp csp) {
		if (enabled && maxSolutionsReached) {
			log("Max-solutions limit reached");
		}
	}

	public static void constraintViolated(Constraint constraint, Solution solution) {
		if (enabled && constraintViolated) {
			logf("Constraint violated: %s", constraint);
		}
	}

	public static void solutionFound(Solution solution) {
		if (enabled && solutionFound) {
			logf("Found solution: %s", solution);
		}
	}

	public static void endSolve(Set<Solution> solutions) {
		if (enabled && endSolve) {
			logf("Resolution ended: %d solutions found", solutions.size());
		}
	}

}

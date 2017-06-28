package it.addvalue.csp.engine;

import lombok.extern.log4j.Log4j;

import java.util.Set;

@Log4j(topic = "csp")
public class CspDebug {

	public static void beginSolve() {
		log("Resolution started");
	}

	private static void log(Object message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	public static void constraintViolated(Solution solution, Constraint constraint) {
		logf("Constraint violated: " + constraint);
	}

	private static void logf(String format, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format(format, args));
		}
	}

	public static void solution(Solution solution) {
		logf("Current solution: %s", solution);
	}

	public static void progress(Csp csp, Set<Solution> solutions) {
		logf("Found %d solutions out of %d...", solutions.size(), csp.getMaxSolutions());
	}

	public static void endSolve(Set<Solution> solutions) {
		log("Resolution ended: " + solutions.size() + " solutions found");
	}

}

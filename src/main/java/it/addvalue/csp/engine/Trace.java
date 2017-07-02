package it.addvalue.csp.engine;

import lombok.extern.log4j.Log4j;

import java.util.Set;

@Log4j(topic = "csp")
public class Trace {

	public static boolean enabled              = true;
	public static boolean beginSolve           = true;
	public static boolean constraintViolated   = false;
	public static boolean solutionFound        = true;
	public static boolean endSolve             = true;
	public static boolean maxSolutionsReached  = true;
	public static boolean maxIterationsReached = true;

	static void beginSolve(Csp csp) {
		if (enabled && beginSolve) {
			logf("Problem definition:\n%s", csp);
			log("Resolution started");
		}
	}

	private static void logf(String format, Object... args) {
		if (log.isDebugEnabled()) {
			log.debug(String.format(format, args));
		}
	}

	private static void log(Object message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
	}

	static void maxIterationsReached(Csp csp, Results results) {
		if (enabled && maxIterationsReached) {
			log("Max-iterations limit reached");
		}
	}

	static void maxSolutionsReached(Csp csp, Results results) {
		if (enabled && maxSolutionsReached) {
			logf("Max-solutions limit reached in %d iterations", results.getIterations());
		}
	}

	static void constraintViolated(Constraint constraint, Solution solution) {
		if (enabled && constraintViolated) {
			logf("Constraint violated: %s", constraint);
		}
	}

	static void solutionFound(Csp problem, Solution solution, Results results) {
		if (enabled && solutionFound) {
			if (problem.getCostFunction() != null) {
				logf("Found solution at iteration %d: cost %d: %s",
				     results.getIterations(),
				     problem.getCostFunction().evaluate(solution),
				     solution);
			} else {
				logf("Found solution at iteration %d: %s", results.getIterations(), solution);
			}
		}
	}

	static void endSolve(Csp problem, Results results) {
		if (enabled && endSolve) {
			logf("Resolution ended in %d iterations", results.getIterations());
			Set<Solution> solutions = results.getSolutions();
			if (solutions.isEmpty()) {
				log("No solutions found");
			} else {
				StringBuilder sb = new StringBuilder();
				if (solutions.size() == 1) {
					sb.append("1 solution found:\n");
				} else {
					sb.append(solutions.size()).append(" solutions found:");
				}
				if (problem.getCostFunction() != null) {
					for (Solution solution : solutions) {
						sb.append("\ncost ")
						  .append(problem.getCostFunction().evaluate(solution))
						  .append(": ")
						  .append(solution);
					}
				} else {
					for (Solution solution : solutions) {
						sb.append("\n").append(solution);
					}
				}
				log(sb);
			}
		}
	}

}

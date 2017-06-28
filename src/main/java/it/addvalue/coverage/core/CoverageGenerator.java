package it.addvalue.coverage.core;

import it.addvalue.coverage.bean.Allocation;
import it.addvalue.coverage.bean.Input;
import it.addvalue.coverage.bean.Output;
import it.addvalue.coverage.bean.Plan;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.core.constraints.ServiceDailyCallsCoverageConstraint;
import it.addvalue.coverage.core.constraints.ServiceOpeningHoursCoverageConstraint;
import it.addvalue.coverage.core.constraints.TeamLeaderPresenceConstraint;
import it.addvalue.coverage.core.constraints.TotalDailyCallsCoverageConstraint;
import it.addvalue.coverage.core.model.Configuration;
import it.addvalue.coverage.core.model.PlanDay;
import it.addvalue.coverage.core.model.PlanStaff;
import it.addvalue.coverage.core.model.PlanVariable;
import it.addvalue.coverage.core.model.PlanWeek;
import it.addvalue.coverage.core.model.PlanWeekSetBuilder;
import it.addvalue.coverage.core.model.PlanWorkshift;
import it.addvalue.csp.engine.Constraint;
import it.addvalue.csp.engine.CostFunction;
import it.addvalue.csp.engine.Csp;
import it.addvalue.csp.engine.CspSolver;
import it.addvalue.csp.engine.Domain;
import it.addvalue.csp.engine.Solution;
import it.addvalue.csp.engine.Value;
import it.addvalue.csp.engine.Variable;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.utils.CsvUtils.toIntArray;

@Data
public class CoverageGenerator {

	private       int       maxSolutions = Csp.UNBOUNDED;
	private       boolean   fullSearch   = true;
	private final CspSolver solver       = new CspSolver();

	public Output generate(Input input) {
		Csp csp = cspFrom(input);
		Set<Solution> solutions = solve(csp);
		return outputFrom(solutions);
	}

	private Csp cspFrom(Input input) {
		Set<Service> services = input.getServices();
		Map<Long, Service> serviceById = serviceById(input);
		Map<Long, PlanWorkshift> workshiftById = workshiftById(input);
		Map<String, Rule> ruleByKey = ruleByKey(input);
		Configuration configuration = configuration(ruleByKey);
		Set<PlanStaff> staffs = staffs(input, serviceById, configuration.getDefaultDailyCallsHandled());
		Set<PlanWeek> weeks = weeks(input, serviceById, configuration.getUndercoverageTolerancePercentage());

		Csp csp = new Csp();
		csp.setDomains(domains(staffs, weeks, workshiftById));
		csp.setConstraints(constraints(weeks, services));
		csp.setCostFunction(costFunction(weeks));
		csp.setMaxSolutions(maxSolutions);
		csp.setFullSearch(fullSearch);
		return csp;
	}

	private Set<Solution> solve(Csp csp) {
		return solver.solve(csp);
	}

	private Output outputFrom(Set<Solution> solutions) {
		Output output = new Output();
		for (Solution solution : solutions) {
			Plan plan = new Plan();
			output.getPlans().add(plan);

			for (Map.Entry<Variable, Value> assignmentEntry : solution.assignments().entrySet()) {
				PlanVariable variable = (PlanVariable) assignmentEntry.getKey();
				PlanWorkshift value = (PlanWorkshift) assignmentEntry.getValue();

				for (PlanDay day : variable.getWeek().getDays()) {
					Allocation allocation = new Allocation();
					plan.getAllocations().add(allocation);

					allocation.setIdCalendar(day.idCalendar());
					allocation.setIdStaff(variable.idStaff());
					allocation.setIdWorkShift(value.idWorkshift());
				}
			}
		}
		return output;
	}

	private static Map<Long, Service> serviceById(Input input) {
		Map<Long, Service> servicesById = new HashMap<Long, Service>();
		for (Service service : input.getServices()) {
			servicesById.put(service.getId(), service);
		}
		return servicesById;
	}

	private static Map<Long, PlanWorkshift> workshiftById(Input input) {
		Map<Long, PlanWorkshift> workshiftById = new HashMap<Long, PlanWorkshift>();
		for (Workshift workshift : input.getWorkshifts()) {
			workshiftById.put(workshift.getId(), newWorkshift(workshift));
		}
		return workshiftById;
	}

	private static Map<String, Rule> ruleByKey(Input input) {
		Map<String, Rule> rulesByKey = new HashMap<String, Rule>();
		for (Rule rule : input.getRules()) {
			rulesByKey.put(rule.getKey(), rule);
		}
		return rulesByKey;
	}

	private static Configuration configuration(Map<String, Rule> ruleByKey) {
		return new Configuration(ruleByKey);
	}

	private static Set<PlanStaff> staffs(Input input, Map<Long, Service> serviceById, int defaultDailyCallsHandled) {
		Set<PlanStaff> staffs = new HashSet<PlanStaff>();
		for (Staff staff : input.getStaffs()) {
			staffs.add(newStaff(staff, serviceById, defaultDailyCallsHandled));
		}
		return staffs;
	}

	private static Set<PlanWeek> weeks(Input input,
	                                   Map<Long, Service> serviceById,
	                                   int undercoverageTolerancePercentage) {

		PlanWeekSetBuilder weekSetBuilder = new PlanWeekSetBuilder();
		for (PlanCalendar calendar : input.getCalendars()) {
			PlanWeek week = weekSetBuilder.add(calendar);
			week.getDays().add(newDay(calendar, serviceById, undercoverageTolerancePercentage));
		}
		return weekSetBuilder.weeks();
	}

	private static Map<Variable, Domain> domains(Iterable<PlanStaff> staffs,
	                                             Iterable<PlanWeek> weeks,
	                                             Map<Long, PlanWorkshift> workshiftById) {
		Map<Variable, Domain> domains = new HashMap<Variable, Domain>();
		for (PlanStaff staff : staffs) {
			for (PlanWeek week : weeks) {
				PlanVariable variable = newVariable(staff, week);
				Domain domain = domain(staff, workshiftById);
				domains.put(variable, domain);

				week.getInvolvedVariables().add(variable);
			}
		}
		return domains;
	}

	private static HashSet<Constraint> constraints(Set<PlanWeek> weeks, Set<Service> services) {
		HashSet<Constraint> constraints = new HashSet<Constraint>();
		for (PlanWeek week : weeks) {
			for (PlanDay day : week.getDays()) {
				for (Service service : services) {
					constraints.add(new ServiceDailyCallsCoverageConstraint(week.getInvolvedVariables(), service, day));
					constraints.add(new ServiceOpeningHoursCoverageConstraint(week.getInvolvedVariables(),
					                                                          service,
					                                                          day));
				}
				constraints.add(new TotalDailyCallsCoverageConstraint(week.getInvolvedVariables(), services, day));
				constraints.add(new TeamLeaderPresenceConstraint(week.getInvolvedVariables(), services, day));
			}
		}
		return constraints;
	}

	private static CostFunction costFunction(Set<PlanWeek> weeks) {
		// TODO aggiungere cost functions
//		CompositeCostFunction costFunction = new CompositeCostFunction();
//		costFunction.addDelegate(new WeightedCostFunction(-1, new UsagePrioritySumCostFunction()));
//		return costFunction;
		return null;
	}

	private static PlanWorkshift newWorkshift(Workshift workshift) {
		PlanWorkshift planWorkshift = new PlanWorkshift(workshift);
		for (Map.Entry<String, String> dailyScheduleEntry : workshift.getDailySchedule().entrySet()) {
			String weekDayName = dailyScheduleEntry.getKey();
			String dailySchedule = dailyScheduleEntry.getValue();
			planWorkshift.getDailySchedule().put(weekDayName, toIntArray(dailySchedule));
		}
		return planWorkshift;
	}

	private static PlanStaff newStaff(Staff staff, Map<Long, Service> serviceById, int defaultDailyCallsHandled) {
		PlanStaff planStaff = new PlanStaff(staff);
		for (Skill skill : staff.getSkills()) {
			Service service = serviceById.get(skill.getIdService());
			planStaff.getPerformances().put(service, new PlanStaff.Performance(skill, defaultDailyCallsHandled));
		}
		return planStaff;
	}

	private static PlanDay newDay(PlanCalendar calendar,
	                              Map<Long, Service> serviceById,
	                              int undercoverageTolerancePercentage) {

		PlanDay day = new PlanDay(calendar, toIntArray(calendar.getTotalExpectedCallsDetail()));
		for (PlanCalendarDetail detail : calendar.getDetails()) {
			Service service = serviceById.get(detail.getIdService());
			int[] dailyCallsDetail = toIntArray(service.getDailyCallsDetail());
			int[] markedDailyCallsDetail = percentage(dailyCallsDetail, detail.getMarkerMultiplier());
			int[] markedDailyCallsDetailWithTolerance =
					decreaseByPercentage(markedDailyCallsDetail, undercoverageTolerancePercentage);

			day.getMarkedDailyCallsDetailByService().put(service, markedDailyCallsDetailWithTolerance);
		}
		return day;
	}

	private static PlanVariable newVariable(PlanStaff staff, PlanWeek week) {
		return new PlanVariable(staff, week);
	}

	private static Domain domain(PlanStaff staff, Map<Long, PlanWorkshift> planValueByWorkshiftId) {
		Domain domain = new Domain();
		for (Long idWorkshift : staff.getStaff().getIdWorkshifts()) {
			domain.add(planValueByWorkshiftId.get(idWorkshift));
		}
		return domain;
	}

	private static int[] percentage(int[] source, int percentage) {
		double factor = percentage * 0.01;
		int[] results = new int[source.length];
		for (int i = 0; i < source.length; i++) {
			results[i] = (int) Math.round(((double) source[i]) * factor);
		}
		return results;
	}

	private static int[] decreaseByPercentage(int[] source, int percentage) {
		return percentage(source, 100 - percentage);
	}

}


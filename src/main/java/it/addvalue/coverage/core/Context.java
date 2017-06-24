package it.addvalue.coverage.core;

import it.addvalue.coverage.Input;
import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.PlanCalendarDetail;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.csp.engine.Constraint;
import it.addvalue.csp.engine.CostFunction;
import it.addvalue.csp.engine.Domain;
import it.addvalue.csp.engine.Solution;
import it.addvalue.csp.engine.Variable;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Context {

	private final Set<Service>          services;
	private final Map<Long, Service>    serviceById;
	private final Map<Long, PlanValue>  planValueByWorkshiftId;
	private final Set<PlanStaff>        planStaffs;
	private final Set<PlanWeek>         planWeeks;
	@Getter
	private final Map<Variable, Domain> domains;
	@Getter
	private final Set<Constraint>       constraints;

	public Context(Input input) {
		services = input.getServices();
		serviceById = serviceById(input);
		planValueByWorkshiftId = planValueByWorkshiftId(input);
		planStaffs = planStaffs(input, serviceById);
		planWeeks = planWeeks(input, serviceById);
		domains = domains(planStaffs, planWeeks, planValueByWorkshiftId);

		constraints = new HashSet<Constraint>();

		for (PlanWeek planWeek : planWeeks) {
			final Set<PlanVariable> constraintVariables = constraintVariables(domains.keySet(), planWeek);
			for (final Service service : services) {
				for (final PlanDay day : planWeek.getDays()) {

					constraints.add(new Constraint() {

						public Set<? extends Variable> variables() {
							return constraintVariables;
						}

						public boolean verify(Solution solution) {
							DailyCallsAccumulator dailyCallsAccumulator = new DailyCallsAccumulator();
							PlanCalendar planCalendar = day.getPlanCalendar();

							for (PlanVariable variable : constraintVariables) {
								PlanValue value = (PlanValue) solution.evaluate(variable);
								PlanStaff planStaff = variable.getPlanStaff();

								if (planStaff.canHandle(service) && value.isScheduled(planCalendar)) {
									dailyCallsAccumulator.add(value.dailyScheduleOn(planCalendar),
									                          planStaff.hourlyCallsFor(service));
								}
							}

							// FIXME introdurre tolleranza del 20%

							return dailyCallsAccumulator.covers(day.markedDailyCallsDetailFor(service));
						}

					});

					constraints.add(new Constraint() {

						public Set<? extends Variable> variables() {
							return constraintVariables;
						}

						public boolean verify(Solution solution) {
							DailyCoverageMerger dailyCoverageMerger = new DailyCoverageMerger();
							PlanCalendar planCalendar = day.getPlanCalendar();

							for (PlanVariable variable : constraintVariables) {
								PlanValue value = (PlanValue) solution.evaluate(variable);

								if (value.isScheduled(planCalendar)) {
									dailyCoverageMerger.add(value.dailyScheduleOn(planCalendar));
								}
							}

							return dailyCoverageMerger.covers(service.getCoverageFrom(), service.getCoverageTo());
						}

					});
				}
			}
		}

		// TODO altri vincoli qui ...
	}

	private static Map<Long, Service> serviceById(Input input) {
		Map<Long, Service> servicesById = new HashMap<Long, Service>();
		for (Service service : input.getServices()) {
			servicesById.put(service.getId(), service);
		}
		return servicesById;
	}

	private static Map<Long, PlanValue> planValueByWorkshiftId(Input input) {
		Map<Long, PlanValue> planValueByWorkshiftId = new HashMap<Long, PlanValue>();
		for (Workshift workshift : input.getWorkshifts()) {
			planValueByWorkshiftId.put(workshift.getId(), planValue(workshift));
		}
		return planValueByWorkshiftId;
	}

	private static Set<PlanStaff> planStaffs(Input input, Map<Long, Service> serviceById) {
		Set<PlanStaff> planStaffs = new HashSet<PlanStaff>();
		for (Staff staff : input.getStaffs()) {
			planStaffs.add(planStaff(staff, serviceById));
		}
		return planStaffs;
	}

	private static Set<PlanWeek> planWeeks(Input input, Map<Long, Service> serviceById) {
		PlanWeekSetBuilder planWeekSetBuilder = new PlanWeekSetBuilder();
		for (PlanCalendar planCalendar : input.getCalendars()) {
			PlanWeek planWeek = planWeekSetBuilder.add(planCalendar);
			PlanDay day = new PlanDay(planCalendar);
			planWeek.getDays().add(day);
			for (PlanCalendarDetail planCalendarDetail : planCalendar.getDetails()) {
				Service service = serviceById.get(planCalendarDetail.getIdService());
				day.computeMarkedDailyCallsDetailFor(service, planCalendarDetail);
			}
		}
		return planWeekSetBuilder.planWeeks();
	}

	private static Map<Variable, Domain> domains(Iterable<PlanStaff> planStaffs,
	                                             Iterable<PlanWeek> planWeeks,
	                                             Map<Long, PlanValue> planValueByWorkshiftId) {
		Map<Variable, Domain> domains = new HashMap<Variable, Domain>();
		for (PlanStaff planStaff : planStaffs) {
			for (PlanWeek planWeek : planWeeks) {
				Variable variable = variable(planStaff, planWeek);
				Domain domain = domain(planStaff, planValueByWorkshiftId);
				domains.put(variable, domain);
			}
		}
		return domains;
	}

	private Set<PlanVariable> constraintVariables(Set<Variable> allVariables, PlanWeek planWeek) {
		Set<PlanVariable> variables = new HashSet<PlanVariable>();
		for (Variable variable : allVariables) {
			PlanVariable planVariable = (PlanVariable) variable;
			if (planVariable.getPlanWeek().equals(planWeek)) {
				variables.add(planVariable);
			}
		}
		return variables;
	}

	private static PlanValue planValue(Workshift workshift) {
		return new PlanValue(workshift);
	}

	private static PlanStaff planStaff(Staff staff, Map<Long, Service> serviceById) {
		PlanStaff planStaff = new PlanStaff(staff);
		for (Skill skill : staff.getSkills()) {
			Service service = serviceById.get(skill.getIdService());
			planStaff.getPerformances().put(service, new PlanStaff.Performance(skill));
		}
		return planStaff;
	}

	private static Variable variable(PlanStaff planStaff, PlanWeek planWeek) {
		return new PlanVariable(planStaff, planWeek);
	}

	private static Domain domain(PlanStaff planStaff, Map<Long, PlanValue> planValueByWorkshiftId) {
		Domain domain = new Domain();
		for (Long idWorkshift : planStaff.getStaff().getIdWorkshifts()) {
			domain.add(planValueByWorkshiftId.get(idWorkshift));
		}
		return domain;
	}

	public CostFunction getCostFunction() {
		// TODO definire costFunction
		return null;
	}

}

package it.addvalue.coverage;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Staff>        staffs;
	private Set<PlanCalendar> calendars;
	private Set<Rule>         rules;
	private Set<Service>      services;
	private Set<Workshift>    workshifts;

}

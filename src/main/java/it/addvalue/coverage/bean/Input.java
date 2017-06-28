package it.addvalue.coverage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Staff>        staffs     = new HashSet<Staff>();
	private Set<PlanCalendar> calendars  = new HashSet<PlanCalendar>();
	private Set<Rule>         rules      = new HashSet<Rule>();
	private Set<Service>      services   = new HashSet<Service>();
	private Set<Workshift>    workshifts = new HashSet<Workshift>();

}

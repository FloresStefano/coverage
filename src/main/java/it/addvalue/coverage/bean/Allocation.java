package it.addvalue.coverage.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Allocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Staff        staff;
	private Workshift    workshift;
	private PlanCalendar calendar;

}

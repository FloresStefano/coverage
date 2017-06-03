package it.addvalue.coverage.bean;

import java.io.Serializable;

public class Allocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Staff        staff;
	private Workshift    workshift;
	private PlanCalendar calendar;

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Workshift getWorkshift() {
		return workshift;
	}

	public void setWorkshift(Workshift workshift) {
		this.workshift = workshift;
	}

	public PlanCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(PlanCalendar calendar) {
		this.calendar = calendar;
	}

}

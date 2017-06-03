package it.addvalue.coverage;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Staff;

import java.io.Serializable;
import java.util.List;

public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Staff> staffList;

	private List<PlanCalendar> calendarList;

	private List<Rule> ruleList;

	public List<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}

	public List<PlanCalendar> getCalendarList() {
		return calendarList;
	}

	public void setCalendarList(List<PlanCalendar> calendarList) {
		this.calendarList = calendarList;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

}

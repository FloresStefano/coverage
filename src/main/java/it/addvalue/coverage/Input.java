package it.addvalue.coverage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;

public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Staff> staffList;

	private List<PlanCalendar> calendarList;

	private List<Rule> ruleList;

	private Map<String, Service> serviceMap;

	private Map<String, Workshift> workshiftMap;

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

	public Map<String, Service> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<String, Service> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public Map<String, Workshift> getWorkshiftMap() {
		return workshiftMap;
	}

	public void setWorkshiftMap(Map<String, Workshift> workshiftMap) {
		this.workshiftMap = workshiftMap;
	}



}

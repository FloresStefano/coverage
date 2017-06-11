package it.addvalue.coverage.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Allocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Staff        staff;
	private Workshift    workshift;
	private PlanCalendar calendar;

	private Long idCalendar;

	private Long idStaff;

	private Long idWorkShift;

	public Long getIdCalendar() {
		return idCalendar;
	}

	public void setIdCalendar(Long idCalendar) {
		this.idCalendar = idCalendar;
	}

	public Long getIdStaff() {
		return idStaff;
	}

	public void setIdStaff(Long idStaff) {
		this.idStaff = idStaff;
	}

	public Long getIdWorkShift() {
		return idWorkShift;
	}

	public void setIdWorkShift(Long idWorkShift) {
		this.idWorkShift = idWorkShift;
	}
	

}

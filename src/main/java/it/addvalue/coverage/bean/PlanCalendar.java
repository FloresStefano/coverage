package it.addvalue.coverage.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PlanCalendar implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Date day;

	private Integer weekOfYear;

	private int dayOfWeek;

	private Integer totalExpectedCalls;

	private String totalExpectedCallsDetail;

	private List<PlanCalendarDetail> detailList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Integer getWeekOfYear() {
		return weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalExpectedCalls() {
		return totalExpectedCalls;
	}

	public void setTotalExpectedCalls(Integer totalExpectedCalls) {
		this.totalExpectedCalls = totalExpectedCalls;
	}

	public String getTotalExpectedCallsDetail() {
		return totalExpectedCallsDetail;
	}

	public void setTotalExpectedCallsDetail(String totalExpectedCallsDetail) {
		this.totalExpectedCallsDetail = totalExpectedCallsDetail;
	}

	public List<PlanCalendarDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PlanCalendarDetail> detailList) {
		this.detailList = detailList;
	}

}

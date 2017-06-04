package it.addvalue.coverage.bean;

import java.io.Serializable;

public class PlanCalendarDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Service service;

	private String markerName;

	private Integer dailyCallsMarked;

	private String dailyCallsDetailMarked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getMarkerName() {
		return markerName;
	}

	public void setMarkerName(String markerName) {
		this.markerName = markerName;
	}

	public Integer getDailyCallsMarked() {
		return dailyCallsMarked;
	}

	public void setDailyCallsMarked(Integer dailyCallsMarked) {
		this.dailyCallsMarked = dailyCallsMarked;
	}

	public String getDailyCallsDetailMarked() {
		return dailyCallsDetailMarked;
	}

	public void setDailyCallsDetailMarked(String dailyCallsDetailMarked) {
		this.dailyCallsDetailMarked = dailyCallsDetailMarked;
	}

}

package it.addvalue.coverage.bean;

import java.io.Serializable;

public class PlanCalendarMarker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idService;

	private String value;

	private Integer dailyCallsMarked;

	private String dailyCallsDetailMarked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdService() {
		return idService;
	}

	public void setIdService(Long idService) {
		this.idService = idService;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

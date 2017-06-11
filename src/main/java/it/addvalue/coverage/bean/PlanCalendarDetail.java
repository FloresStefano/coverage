package it.addvalue.coverage.bean;

import java.io.Serializable;

public class PlanCalendarDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idService;

	private String markerMultiplier;

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

	public String getMarkerMultiplier() {
		return markerMultiplier;
	}

	public void setMarkerMultiplier(String markerMultiplier) {
		this.markerMultiplier = markerMultiplier;
	}

	

	
}

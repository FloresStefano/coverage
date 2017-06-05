package it.addvalue.coverage.bean;

import java.io.Serializable;

public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer skillLevel;

	private Integer usagePriority;

	private Integer handledCallsOverrided;

	private Long idService;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}

	public Integer getUsagePriority() {
		return usagePriority;
	}

	public void setUsagePriority(Integer usagePriority) {
		this.usagePriority = usagePriority;
	}

	public Integer getHandledCallsOverrided() {
		return handledCallsOverrided;
	}

	public void setHandledCallsOverrided(Integer handledCallsOverrided) {
		this.handledCallsOverrided = handledCallsOverrided;
	}

	public Long getIdService() {
		return idService;
	}

	public void setIdService(Long idService) {
		this.idService = idService;
	}



}

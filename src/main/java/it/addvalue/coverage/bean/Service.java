package it.addvalue.coverage.bean;

import java.io.Serializable;

/**
 * Configurazione servizi per SirioHR
 */

public class Service implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Integer dailyCalls;

	private String dailyCallsDetail;

	private String dailyCallsDetailTemplate;

	private Integer coverageFrom;

	private Integer coverageTo;

	private Integer skillLevel;

	private Integer usagePriority;

	private Integer handledCallsOverrided;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDailyCalls() {
		return dailyCalls;
	}

	public void setDailyCalls(Integer dailyCalls) {
		this.dailyCalls = dailyCalls;
	}

	public String getDailyCallsDetail() {
		return dailyCallsDetail;
	}

	public void setDailyCallsDetail(String dailyCallsDetail) {
		this.dailyCallsDetail = dailyCallsDetail;
	}

	public String getDailyCallsDetailTemplate() {
		return dailyCallsDetailTemplate;
	}

	public void setDailyCallsDetailTemplate(String dailyCallsDetailTemplate) {
		this.dailyCallsDetailTemplate = dailyCallsDetailTemplate;
	}

	public Integer getCoverageFrom() {
		return coverageFrom;
	}

	public void setCoverageFrom(Integer coverageFrom) {
		this.coverageFrom = coverageFrom;
	}

	public Integer getCoverageTo() {
		return coverageTo;
	}

	public void setCoverageTo(Integer coverageTo) {
		this.coverageTo = coverageTo;
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

}

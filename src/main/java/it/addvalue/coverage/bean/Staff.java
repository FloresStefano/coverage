package it.addvalue.coverage.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Configurazione del personale per SirioHR
 */


public class Staff implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long idTeam;

	private String contractName;

	private Date validFrom;

	private Date validTo;

	private List<Skill> skillList;

	private List<Workshift> workshiftList;

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

	public Long getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(Long idTeam) {
		this.idTeam = idTeam;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	public List<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}

	public List<Workshift> getWorkshiftList() {
		return workshiftList;
	}

	public void setWorkshiftList(List<Workshift> workshiftList) {
		this.workshiftList = workshiftList;
	}

	
	
}

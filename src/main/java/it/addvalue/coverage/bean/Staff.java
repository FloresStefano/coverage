package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class Staff implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long   id;
	private String name;
	private Long   idTeam;
	private String contractName;
	private Date   validFrom;
	private Date   validTo;
	private Set<Skill> skills       = new HashSet<Skill>();
	private Set<Long>  idWorkshifts = new HashSet<Long>();

	// TODO introdurre Boolean isTeamleader

}

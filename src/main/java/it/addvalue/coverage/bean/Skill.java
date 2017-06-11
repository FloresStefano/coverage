package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id")
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private Integer skillLevel;
	private Integer usagePriority;
	private Integer handledCallsOverridden;
	private Long    idService;

}

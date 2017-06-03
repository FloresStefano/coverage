package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Configurazione servizi per SirioHR
 */
@Data
@EqualsAndHashCode(of = "id")
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private String  name;
	private Integer dailyCalls;
	private String  dailyCallsDetail;
	private String  dailyCallsDetailTemplate;
	private Integer coverageFrom;
	private Integer coverageTo;
	private Integer skillLevel;
	private Integer usagePriority;
	private Integer handledCallsOverrided;

}

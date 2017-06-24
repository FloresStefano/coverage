package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = "id")
public class PlanCalendarDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long       id;
	private Long       idService;
	private BigDecimal markerMultiplier;

}

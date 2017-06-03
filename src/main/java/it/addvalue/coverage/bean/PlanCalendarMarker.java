package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id")
public class PlanCalendarMarker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private Long    idService;
	private String  value;
	private Integer dailyCallsMarked;
	private String  dailyCallsDetailMarked;

}

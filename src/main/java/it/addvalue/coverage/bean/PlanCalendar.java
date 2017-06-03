package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Configurazione calendario (=insieme di giorni) del piano di programmazione (scheduling plan) per sirioHR
 */
@Data
@EqualsAndHashCode(of = "id")
public class PlanCalendar implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long                     id;
	private String                   name;
	private Date                     day;
	private Integer                  weekOfYear;
	private int                      dayOfWeek;
	private Integer                  expectedCalls;
	private String                   expectedCallsDetail;
	private List<PlanCalendarMarker> markerList;

}

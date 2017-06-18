package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
public class PlanCalendar implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long    id;
	private String  name;
	private Date    day;
	private Integer weekOfYear;
	private Integer dayOfWeek;
	private Integer totalExpectedCalls;
	private String  totalExpectedCallsDetail;
	private Set<PlanCalendarDetail> details = new HashSet<PlanCalendarDetail>();

}

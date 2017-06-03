package it.addvalue.coverage;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Staff;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Staff>        staffList;
	private List<PlanCalendar> calendarList;
	private List<Rule>         ruleList;

}

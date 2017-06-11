package it.addvalue.coverage;

import it.addvalue.coverage.bean.PlanCalendar;
import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class Input implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Staff>            staffList;
	private List<PlanCalendar>     calendarList;
	private List<Rule>             ruleList;
	private Map<String, Service>   serviceMap;
	private Map<String, Workshift> workshiftMap;

}

package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(of = "id")
public class Workshift implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long   id;
	private String name;
	private String contractName;
	private Map<String, String> dailySchedule = new HashMap<String, String>();

}

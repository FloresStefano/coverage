package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id")
public class Rule implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long   id;
	private String name;
	private String key;
	private String value;
	private String weight;

}

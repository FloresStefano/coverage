package it.addvalue.coverage.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class Allocation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idCalendar;
	private Long idStaff;
	private Long idWorkShift;

}

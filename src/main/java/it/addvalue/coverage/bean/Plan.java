package it.addvalue.coverage.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Plan implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<Allocation> allocations = new ArrayList<Allocation>();

}

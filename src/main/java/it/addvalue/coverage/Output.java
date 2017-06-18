package it.addvalue.coverage;

import it.addvalue.coverage.bean.Allocation;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Output {

	private Set<Set<Allocation>> allocations = new HashSet<Set<Allocation>>();

}

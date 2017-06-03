package it.addvalue.coverage;

import it.addvalue.coverage.bean.Allocation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Output {

	private List<List<Allocation>> allocationList = new ArrayList<List<Allocation>>();

}

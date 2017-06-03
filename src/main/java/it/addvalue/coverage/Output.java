package it.addvalue.coverage;

import it.addvalue.coverage.bean.Allocation;

import java.util.ArrayList;
import java.util.List;

public class Output {

	private List<List<Allocation>> allocationList = new ArrayList<List<Allocation>>();

	public List<List<Allocation>> getAllocationList() {
		return allocationList;
	}

	public void setAllocationList(List<List<Allocation>> allocationList) {
		this.allocationList = allocationList;
	}

}

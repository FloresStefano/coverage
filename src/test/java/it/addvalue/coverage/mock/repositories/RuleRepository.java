package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Rule;

import java.util.HashMap;
import java.util.Map;

public class RuleRepository {

	public final Map<Long, Rule> data = new HashMap<Long, Rule>();
	private      long            id   = 0;

	public Rule newItem() {
		Rule item = new Rule();
		item.setId(id);
		item.setName("Rule" + id);
		data.put(id, item);
		id++;
		return item;
	}

}

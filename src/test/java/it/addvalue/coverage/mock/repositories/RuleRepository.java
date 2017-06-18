package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Rule;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class RuleRepository implements Serializable {

	public final Map<Long, Rule> data = new HashMap<Long, Rule>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Rule newItem() {
		val item = new Rule();
		item.setId(id);
		item.setName("Rule" + id);
		data.put(id, item);
		id++;
		return item;
	}

}

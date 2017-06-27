package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Rule;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class RuleRepository implements Serializable {

	public final Map<Long, Rule> data = new HashMap<Long, Rule>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final Rule item = new Rule();

		public Insert() {
			item.setName("Rule" + id);
		}

		public Insert withName(String name) {
			item.setName(name);
			return this;
		}

		public Insert withKey(String key) {
			item.setKey(key);
			return this;
		}

		public Insert withValue(String value) {
			item.setValue(value);
			return this;
		}

		public Insert withWeight(String weight) {
			item.setWeight(weight);
			return this;
		}

		public Rule commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}

	}

}

package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public class SkillRepository implements Serializable {

	public final Map<Long, Skill> data = new HashMap<Long, Skill>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	public class Insert {

		private final Skill item = new Skill();

		public Insert withSkillLevel(int skillLevel) {
			item.setSkillLevel(skillLevel);
			return this;
		}

		public Insert withUsagePriority(int usagePriority) {
			item.setUsagePriority(usagePriority);
			return this;
		}

		public Insert withHandledCalls(int handledCallsOverridden) {
			item.setHandledCallsOverridden(handledCallsOverridden);
			return this;
		}

		public Insert withService(Service service) {
			item.setIdService(service.getId());
			return this;
		}

		public Skill commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}

	}

}

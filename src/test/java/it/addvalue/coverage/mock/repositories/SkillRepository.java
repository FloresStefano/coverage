package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static it.addvalue.coverage.mock.utils.RandomUtils.randomInRangeInclusively;

@EqualsAndHashCode
public class SkillRepository implements Serializable {

	public final Map<Long, Skill> data = new HashMap<Long, Skill>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Skill newItem(Service service) {
		Skill item = new Skill();
		item.setId(id);
		item.setHandledCallsOverridden(randomInRangeInclusively(40, 50));
		item.setSkillLevel(randomInRangeInclusively(4, 10));
		item.setUsagePriority(randomInRangeInclusively(0, 10));
		item.setIdService(service.getId());
		data.put(id, item);
		id++;
		return item;
	}

}

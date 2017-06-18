package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static it.addvalue.coverage.mock.repositories.WorkshiftRepository.CONTRACTNAME;

@EqualsAndHashCode
public class StaffRepository implements Serializable {

	public final Map<Long, Staff> data = new HashMap<Long, Staff>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Staff newItem(Set<Skill> skills, Set<Workshift> workshifts) {
		val item = new Staff();
		item.setId(id);
		item.setName("Staff" + id);
		item.setContractName(CONTRACTNAME);
		item.setIdTeam(0L);
		item.setSkills(skills);
		item.setIdWorkshifts(idWorkshifts(workshifts));
		data.put(id, item);
		id++;
		return item;
	}

	private static Set<Long> idWorkshifts(Set<Workshift> workshifts) {
		val ids = new HashSet<Long>();
		for (val workshift : workshifts) {
			ids.add(workshift.getId());
		}
		return ids;
	}

}

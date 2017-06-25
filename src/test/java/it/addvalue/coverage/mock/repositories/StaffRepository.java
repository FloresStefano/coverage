package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import lombok.EqualsAndHashCode;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Date;
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

	public Staff newItem(Set<Skill> skills,
	                     Set<Workshift> workshifts,
	                     LocalDate validFrom,
	                     LocalDate validTo,
	                     long idTeam,
	                     boolean isTeamLeader) {
		Staff item = new Staff();
		item.setId(id);
		item.setName("Staff" + id);
		item.setContractName(CONTRACTNAME);
		item.setIdTeam(idTeam);
		item.setTeamLeader(isTeamLeader);
		item.setSkills(skills);
		item.setValidFrom(nullSafeDate(validFrom));
		item.setValidTo(nullSafeDate(validTo));
		item.setIdWorkshifts(idWorkshifts(workshifts));
		data.put(id, item);
		id++;
		return item;
	}

	private static Date nullSafeDate(LocalDate date) {
		return date == null ? null : date.toDate();
	}

	private static Set<Long> idWorkshifts(Set<Workshift> workshifts) {
		Set<Long> ids = new HashSet<Long>();
		for (Workshift workshift : workshifts) {
			ids.add(workshift.getId());
		}
		return ids;
	}

}

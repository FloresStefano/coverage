package it.addvalue.coverage.mock.repositories;

import it.addvalue.coverage.bean.Skill;
import it.addvalue.coverage.bean.Staff;
import it.addvalue.coverage.bean.Workshift;
import it.addvalue.coverage.mock.utils.Iso8601Utils;
import lombok.EqualsAndHashCode;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static it.addvalue.coverage.mock.repositories.WorkshiftRepository.CONTRACTNAME;

@EqualsAndHashCode
public class StaffRepository implements Serializable {

	public final Map<Long, Staff> data = new HashMap<Long, Staff>();

	private static final long serialVersionUID = 1L;

	private long id = 0;

	public Insert insert() {
		return new Insert();
	}

	private static Date nullSafeDate(LocalDate date) {
		return date == null ? null : date.toDate();
	}

	public class Insert {

		private final Staff item = new Staff();

		public Insert() {
			item.setContractName(CONTRACTNAME);
			item.setName("Staff" + id);
		}

		public Insert withName(String name) {
			item.setName(name);
			return this;
		}

		public Insert withIdTeam(Long idTeam) {
			item.setIdTeam(idTeam);
			return this;
		}

		public Insert withTeamLeader(Boolean teamLeader) {
			item.setTeamLeader(teamLeader);
			return this;
		}

		public Insert withContractName(String contractName) {
			item.setContractName(contractName);
			return this;
		}

		public Insert withValidFrom(String iso8601ValidFrom) {
			item.setValidFrom(Iso8601Utils.parse(iso8601ValidFrom));
			return this;
		}

		public Insert withValidFrom(LocalDate validFrom) {
			item.setValidFrom(validFrom.toDate());
			return this;
		}

		public Insert withvalidTo(String iso8601ValidTo) {
			item.setValidTo(Iso8601Utils.parse(iso8601ValidTo));
			return this;
		}

		public Insert withvalidTo(LocalDate validTo) {
			item.setValidTo(validTo.toDate());
			return this;
		}

		public Insert withSkill(Skill skill) {
			item.getSkills().add(skill);
			return this;
		}

		public Insert withSkills(Iterable<Skill> skills) {
			for (Skill skill : skills) {
				item.getSkills().add(skill);
			}
			return this;
		}

		public Insert withWorkshift(Workshift workshift) {
			item.getIdWorkshifts().add(workshift.getId());
			return this;
		}

		public Insert withWorkshifts(Iterable<Workshift> workshifts) {
			for (Workshift workshift : workshifts) {
				item.getIdWorkshifts().add(workshift.getId());
			}
			return this;
		}

		public Staff commit() {
			item.setId(id);
			data.put(id, item);
			id++;
			return item;
		}
	}

}

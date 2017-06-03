package coverage.mock;

import it.addvalue.coverage.bean.Rule;
import it.addvalue.coverage.utils.XmlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class RuleMock {

	private static final int RULE_COUNT = 10;

	public static List<Rule> mock() {
		List<Rule> list = new ArrayList<Rule>();
		for (long i = 0; i < RULE_COUNT; i++) {
			Rule e = new Rule();
			e.setName("rule" + i);
			e.setId(i);

			list.add(e);
		}
		return list;
	}

	@Test
	public void testmock()
	throws IOException {
		assertNotNull(XmlUtil.prettyPrint(mock()));
	}
}

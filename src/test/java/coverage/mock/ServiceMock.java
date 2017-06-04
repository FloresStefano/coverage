package coverage.mock;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import it.addvalue.coverage.bean.Service;
import it.addvalue.coverage.bean.Skill;

public class ServiceMock {

	public static Map<Integer, Service> serviceMap;
	static {
		serviceMap = new HashMap<Integer, Service>();
		serviceMap.put(0, mockService(0L));
		serviceMap.put(1, mockService(1L));
		serviceMap.put(2, mockService(2L));
		serviceMap.put(3, mockService(3L));
		serviceMap.put(4, mockService(4L));
		serviceMap.put(5, mockService(5L));
	}

	public static List<Skill> skillMock() {

		List<Skill> list = new ArrayList<Skill>();
		for (long i = 0; i < random(1, 3); i++) {
			Random generator = new Random();
			Object[] values = serviceMap.values().toArray();
			Service randomService = (Service) values[generator
					.nextInt(values.length)];
			Skill skill = new Skill();
			skill.setHandledCallsOverrided(random(40, 50));
			skill.setSkillLevel(random(4, 10));
			skill.setUsagePriority(10);
			skill.setService(randomService);
			list.add(skill);
		}
		return list;
	}

	private static Service mockService(long l) {
		Service e = new Service();
		e.setId(l);
		e.setName("service" + l);
		e.setCoverageFrom(400);
		e.setCoverageTo(900);
		e.setDailyCalls(1000);
		e.setDailyCallsDetail("200,400,400,400,400,200");

		return e;
	}

	private static int random(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Test
	public void testmock() throws IOException {
		XmlMapper xmlMapper = new XmlMapper();
		String writeValueAsString = xmlMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(skillMock());
		System.out.println(writeValueAsString);
		assertNotNull(writeValueAsString);
	}
}

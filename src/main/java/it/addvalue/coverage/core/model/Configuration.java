package it.addvalue.coverage.core.model;

import it.addvalue.coverage.bean.Rule;
import lombok.Data;

import java.util.Map;

@Data
public class Configuration {

	private final int defaultDailyCallsHandled;
	private final int undercoverageTolerancePercentage;

	public Configuration(Map<String, Rule> ruleByKey) {
		defaultDailyCallsHandled = defaultDailyCallsHandled(ruleByKey);
		undercoverageTolerancePercentage = undercoverageTolerancePercentage(ruleByKey);
	}

	private static int defaultDailyCallsHandled(Map<String, Rule> ruleByKey) {
		return intRuleValue("default-daily-calls-handled", 50, ruleByKey);
	}

	private static int undercoverageTolerancePercentage(Map<String, Rule> ruleByKey) {
		return intRuleValue("undercoverage-tolerance-percentage", 20, ruleByKey);
	}

	private static int intRuleValue(String key, int defaultValue, Map<String, Rule> ruleByKey) {
		Rule rule = ruleByKey.get(key);
		return rule == null ? defaultValue : Integer.parseInt(rule.getValue());
	}

}

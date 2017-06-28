package it.addvalue.coverage.core.model;

import it.addvalue.coverage.bean.Rule;
import lombok.Data;

import java.util.Map;

import static it.addvalue.coverage.core.model.RuleKeys.DEFAULT_DAILY_CALLS_HANDLED;
import static it.addvalue.coverage.core.model.RuleKeys.UNDERCOVERAGE_TOLERANCE_PERCENTAGE;

@Data
public class Configuration {

	private final int defaultDailyCallsHandled;
	private final int undercoverageTolerancePercentage;

	public Configuration(Map<String, Rule> ruleByKey) {
		defaultDailyCallsHandled = defaultDailyCallsHandled(ruleByKey);
		undercoverageTolerancePercentage = undercoverageTolerancePercentage(ruleByKey);
	}

	private static int defaultDailyCallsHandled(Map<String, Rule> ruleByKey) {
		return intRuleValue(DEFAULT_DAILY_CALLS_HANDLED, 50, ruleByKey);
	}

	private static int undercoverageTolerancePercentage(Map<String, Rule> ruleByKey) {
		return intRuleValue(UNDERCOVERAGE_TOLERANCE_PERCENTAGE, 20, ruleByKey);
	}

	private static int intRuleValue(String key, int defaultValue, Map<String, Rule> ruleByKey) {
		Rule rule = ruleByKey.get(key);
		return rule == null ? defaultValue : Integer.parseInt(rule.getValue());
	}

}

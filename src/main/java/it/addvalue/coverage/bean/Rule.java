package it.addvalue.coverage.bean;

import java.io.Serializable;

/**
 * Configurazione regole per sirioHR - hanno una gestione simile a quella delle property
 */

public class Rule implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            name;

    private String            ruleKey;

    private String            ruleValue;

    private String            ruleWeight;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRuleKey()
    {
        return ruleKey;
    }

    public void setRuleKey(String ruleKey)
    {
        this.ruleKey = ruleKey;
    }

    public String getRuleValue()
    {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue)
    {
        this.ruleValue = ruleValue;
    }

    public String getRuleWeight()
    {
        return ruleWeight;
    }

    public void setRuleWeight(String ruleWeight)
    {
        this.ruleWeight = ruleWeight;
    }

}

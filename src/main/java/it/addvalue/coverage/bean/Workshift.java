package it.addvalue.coverage.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Configurazione dei turni per SirioHR
 */

public class Workshift implements Serializable
{

    private static final long   serialVersionUID = 1L;

    private Long                id;

    private String              name;

    private String              contractName;

    private Map<Integer, int[]> dailySchedule;

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

    public String getContractName()
    {
        return contractName;
    }

    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }

    public Map<Integer, int[]> getDailySchedule()
    {
        return dailySchedule;
    }

    public void setDailySchedule(Map<Integer, int[]> dailySchedule)
    {
        this.dailySchedule = dailySchedule;
    }

   

}

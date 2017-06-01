package it.addvalue.coverage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Configurazione dei turni per SirioHR
 */

public class Workshift implements Serializable
{

    private static final long   serialVersionUID = 1L;

    private Long                id;

    private String              name;

    private String              contractName;

    private List<DailySchedule> dailyScheduleList;

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

    public List<DailySchedule> getDailyScheduleList()
    {
        return dailyScheduleList;
    }

    public void setDailyScheduleList(List<DailySchedule> dailyScheduleList)
    {
        this.dailyScheduleList = dailyScheduleList;
    }

}

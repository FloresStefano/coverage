package it.addvalue.coverage.bean;

import java.io.Serializable;

/**
 * @author aniello.attratto
 */

public class DailySchedule implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            name;

    private int               dayOfWeek;

    private Integer           minutesFrom;

    private Integer           minutesTo;

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

    public int getDayOfWeek()
    {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getMinutesFrom()
    {
        return minutesFrom;
    }

    public void setMinutesFrom(Integer minutesFrom)
    {
        this.minutesFrom = minutesFrom;
    }

    public Integer getMinutesTo()
    {
        return minutesTo;
    }

    public void setMinutesTo(Integer minutesTo)
    {
        this.minutesTo = minutesTo;
    }

}

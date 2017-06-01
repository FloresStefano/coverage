package it.addvalue.coverage.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Configurazione calendario (=insieme di giorni) del piano do programmazione (scheduling plan) per sirioHR
 */

public class Calendar implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Long              id;

    private String            name;

    private Date              day;

    private Integer           weekOfYear;

    private int               dayOfWeek;

    private Integer           expectedCalls;

    private String            expectedCallsDetail;

    private String            expectedCallsServiceDetail;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getDay()
    {
        return day;
    }

    public void setDay(Date day)
    {
        this.day = day;
    }

    public Integer getWeekOfYear()
    {
        return weekOfYear;
    }

    public void setWeekOfYear(Integer weekOfYear)
    {
        this.weekOfYear = weekOfYear;
    }

    public int getDayOfWeek()
    {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek)
    {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getExpectedCalls()
    {
        return expectedCalls;
    }

    public void setExpectedCalls(Integer expectedCalls)
    {
        this.expectedCalls = expectedCalls;
    }

    public String getExpectedCallsDetail()
    {
        return expectedCallsDetail;
    }

    public void setExpectedCallsDetail(String expectedCallsDetail)
    {
        this.expectedCallsDetail = expectedCallsDetail;
    }

    public String getExpectedCallsServiceDetail()
    {
        return expectedCallsServiceDetail;
    }

    public void setExpectedCallsServiceDetail(String expectedCallsServiceDetail)
    {
        this.expectedCallsServiceDetail = expectedCallsServiceDetail;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
package com.WebProject.VotingSystem.domain;

import java.util.Date;

public class VotingPeriod {
    private Integer VP_ID ;
    private String datetime ;
    private String prop1;
    private String prop2;
    private String prop3;
    private String prop4;

    public VotingPeriod(Integer VP_ID, String datetime, String prop1, String prop2, String prop3, String prop4) {
        this.VP_ID = VP_ID;
        this.datetime = datetime;
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.prop3 = prop3;
        this.prop4 = prop4;
    }

    public Integer getVP_ID() {
        return VP_ID;
    }

    public void setVP_ID(Integer VP_ID) {
        this.VP_ID = VP_ID;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public String getProp3() {
        return prop3;
    }

    public void setProp3(String prop3) {
        this.prop3 = prop3;
    }

    public String getProp4() {
        return prop4;
    }

    public void setProp4(String prop4) {
        this.prop4 = prop4;
    }
}

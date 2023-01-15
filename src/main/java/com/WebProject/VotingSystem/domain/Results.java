package com.WebProject.VotingSystem.domain;

public class Results {
    private Integer VP_ID ;
    private Integer prop1;
    private Integer prop2;
    private Integer prop3;
    private Integer prop4;

    public Results(Integer VP_ID, Integer prop1, Integer prop2, Integer prop3, Integer prop4) {
        this.VP_ID = VP_ID;
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

    public Integer getProp1() {
        return prop1;
    }

    public void setProp1(Integer prop1) {
        this.prop1 = prop1;
    }

    public Integer getProp2() {
        return prop2;
    }

    public void setProp2(Integer prop2) {
        this.prop2 = prop2;
    }

    public Integer getProp3() {
        return prop3;
    }

    public void setProp3(Integer prop3) {
        this.prop3 = prop3;
    }

    public Integer getProp4() {
        return prop4;
    }

    public void setProp4(Integer prop4) {
        this.prop4 = prop4;
    }
}

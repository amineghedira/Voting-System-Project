package com.WebProject.VotingSystem.domain;

public class Resident {
    private Integer residentID;
    private Integer CIN;
    private String fullName;
    private String DOB;
    private String gender;

    public Resident(Integer residentID ,Integer CIN, String fullName, String DOB, String gender) {
        this.residentID= residentID;
        this.CIN = CIN;
        this.fullName = fullName;
        this.DOB = DOB;
        this.gender = gender;
    }

    public Integer getResidentID() {
        return residentID;
    }

    public void setResidentID(Integer residentID) {
        this.residentID = residentID;
    }

    public Integer getCIN() {
        return CIN;
    }

    public void setCIN(Integer CIN) {
        this.CIN = CIN;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

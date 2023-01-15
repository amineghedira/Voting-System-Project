package com.WebProject.VotingSystem.domain;

public class User {
    private Integer userID;
    private Integer CIN;
    private String email;
    private String password;

    public User(Integer userID, Integer CIN, String email, String password) {
        this.userID = userID;
        this.CIN = CIN;
        this.email = email;
        this.password = password;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getCIN() {
        return CIN;
    }

    public void setCIN(Integer CIN) {
        this.CIN = CIN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

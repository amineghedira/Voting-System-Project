package com.WebProject.VotingSystem.domain;

public class VotingHistory {

    private Integer userID;
    private Integer VP_ID;
    private String vote;

    public VotingHistory(Integer userID, Integer VP_ID, String vote) {
        this.userID = userID;
        this.VP_ID = VP_ID;
        this.vote = vote;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getVP_ID() {
        return VP_ID;
    }

    public void setVP_ID(Integer VP_ID) {
        this.VP_ID = VP_ID;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}

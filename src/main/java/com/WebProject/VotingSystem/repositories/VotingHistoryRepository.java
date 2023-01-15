package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.VotingHistory;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

public interface VotingHistoryRepository {
    VotingHistory fetchHistoryByID(Integer userID,Integer VP_ID) throws EtResourceNotFoundException;
    void addVote(Integer userID,Integer VP_ID,String vote) throws EtBadRequestException;
    Integer getCountByID(Integer userID, Integer VP_ID) ;

}

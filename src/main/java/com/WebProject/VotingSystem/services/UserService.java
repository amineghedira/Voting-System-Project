package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.domain.User;
import com.WebProject.VotingSystem.domain.VotingHistory;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;
    User registerUser(Integer CIN, String email, String password) throws EtAuthException;
    Results fetchResultsByID(Integer VP_ID) throws EtResourceNotFoundException;

    VotingHistory fetchHistoryByID(Integer userID, Integer VP_ID)throws EtResourceNotFoundException;
    VotingHistory vote(Integer userID, Integer VP_ID, String vote) throws EtAuthException;
}


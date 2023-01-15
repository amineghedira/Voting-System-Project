package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.User;
import com.WebProject.VotingSystem.exceptions.EtAuthException;

public interface UserRepository {
    Integer create(Integer CIN, String email, String password) throws EtAuthException;
    User findByEmailAndPassword(String email, String password);
    Integer getCountByCIN1(Integer CIN);
    Integer getCountByCIN2(Integer CIN);
    Integer getCountByEmail(String email);
    User findByID(Integer userID);

}

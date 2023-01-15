package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.exceptions.EtAuthException;

public interface AdminRepository {
   Integer create(String adminName, String password) throws EtAuthException;
   Admin findByNameAndPassword(String adminName, String password ) throws EtAuthException;
   Integer getCountByName(String adminName);
   Admin findByID(Integer adminID);
}

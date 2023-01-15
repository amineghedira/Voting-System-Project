package com.WebProject.VotingSystem.services;
import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.exceptions.EtAuthException;

public interface AdminService {

    Admin validateAdmin(String adminName, String password) throws EtAuthException;
    Admin registerAdmin(String adminName, String password) throws EtAuthException;
}

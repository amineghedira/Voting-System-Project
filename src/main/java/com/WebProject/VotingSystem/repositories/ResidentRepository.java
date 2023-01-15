package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface ResidentRepository {
    List<Resident> findAll() throws EtResourceNotFoundException;
    Resident findByID(Integer CIN) throws EtResourceNotFoundException;
    Integer create(Integer CIN, String fullName, String DOB, String gender) throws EtBadRequestException;
    void update(Integer CIN, Resident resident) throws EtBadRequestException;
    void removeByID(Integer CIN);



}

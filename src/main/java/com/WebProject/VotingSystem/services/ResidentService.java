package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface ResidentService {
    List<Resident> fetchAllResidents();
    Resident fetchResidentByID(Integer CIN) throws EtResourceNotFoundException;
    Resident addResident(Integer CIN, String fullName, String DOB, String gender) throws EtBadRequestException;
    void updateResident(Integer CIN, Resident resident) throws EtBadRequestException;
    void removeResidentWithAccount(Integer CIN) throws EtResourceNotFoundException;
}

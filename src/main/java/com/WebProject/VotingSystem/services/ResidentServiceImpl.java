package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import com.WebProject.VotingSystem.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {
    @Autowired
    ResidentRepository residentRepository;
    @Override
    public List<Resident> fetchAllResidents() {
        return residentRepository.findAll();
    }

    @Override
    public Resident fetchResidentByID(Integer CIN) throws EtResourceNotFoundException {
        return residentRepository.findByID(CIN);
    }
    @Override
    public Resident addResident(Integer CIN, String fullName, String DOB, String gender) throws EtBadRequestException {
         int ID = residentRepository.create(CIN, fullName, DOB, gender);
         return residentRepository.findByID(ID);
    }

    @Override
    public void updateResident(Integer CIN, Resident resident) throws EtBadRequestException {
      residentRepository.update(CIN, resident);
    }

    @Override
    public void removeResidentWithAccount(Integer CIN) throws EtResourceNotFoundException {
        residentRepository.removeByID(CIN);

    }
}

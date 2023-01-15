package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import com.WebProject.VotingSystem.repositories.VotingPeriodRepository;

import java.util.List;

public interface VotingPeriodService {

    List<VotingPeriod> fetchAllVotingPeriods();
    VotingPeriod fetchVotingPeriodByID(Integer VP_ID) throws EtResourceNotFoundException;
    VotingPeriod addVotingPeriod(String prop1, String prop2, String prop3, String prop4) throws EtBadRequestException;
    VotingPeriod fetchLatestVotingPeriod() throws EtResourceNotFoundException;
}

package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface VotingPeriodRepository {
    List<VotingPeriod> findAll() throws EtResourceNotFoundException;
    VotingPeriod findByID(Integer VP_ID) throws EtResourceNotFoundException;
    VotingPeriod findLatest() throws EtResourceNotFoundException;

    Integer create(String datetime, String prop1, String prop2, String prop3, String prop4) throws EtBadRequestException;
}

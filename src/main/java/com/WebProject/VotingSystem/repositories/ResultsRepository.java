package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;

public interface ResultsRepository {

    Results findByID(Integer VP_ID) throws EtResourceNotFoundException;
    void addVote(Integer VP_ID, String vote) throws EtBadRequestException;

    void create(Integer VP_ID, Integer prop1, Integer prop2, Integer prop3, Integer prop4) throws EtBadRequestException;


}

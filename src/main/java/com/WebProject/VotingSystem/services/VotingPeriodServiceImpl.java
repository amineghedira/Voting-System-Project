package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import com.WebProject.VotingSystem.repositories.ResultsRepository;
import com.WebProject.VotingSystem.repositories.VotingPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class VotingPeriodServiceImpl implements VotingPeriodService {
    @Autowired
    VotingPeriodRepository votingPeriodRepository;
    @Autowired
    ResultsRepository resultsRepository;

    @Override
    public List<VotingPeriod> fetchAllVotingPeriods() {
        return votingPeriodRepository.findAll();
    }

    @Override
    public VotingPeriod fetchVotingPeriodByID(Integer VP_ID) throws EtResourceNotFoundException {
        return votingPeriodRepository.findByID(VP_ID);
    }

    @Override
    public VotingPeriod fetchLatestVotingPeriod() throws EtResourceNotFoundException {
        VotingPeriod latestVotingPeriod = votingPeriodRepository.findLatest();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
             String datetime = latestVotingPeriod.getDatetime();
             Date creationDate= sdf.parse(datetime);
            Date now = new Date(System.currentTimeMillis());
            long difference = (now.getTime()- creationDate.getTime())/1000/60/60 ;
            if(difference>24)
                throw new EtResourceNotFoundException("currently, there is no voting period");
            } catch (ParseException e) {e.printStackTrace();}
        return latestVotingPeriod;
      }

    @Override
    public VotingPeriod addVotingPeriod(String prop1, String prop2, String prop3, String prop4) throws EtBadRequestException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String datetime = sdf.format(System.currentTimeMillis());
        Integer VP_ID = votingPeriodRepository.create(datetime, prop1, prop2, prop3, prop4);
        Integer p3 =0, p4= 0 ;
        if (prop3==null)
            p3= null;
        if (prop4==null)
            p4=null;
        resultsRepository.create(VP_ID,0,0, p3, p4);
        return votingPeriodRepository.findByID(VP_ID);
    }
}

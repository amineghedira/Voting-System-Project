package com.WebProject.VotingSystem.services;

import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.domain.User;
import com.WebProject.VotingSystem.domain.VotingHistory;
import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import com.WebProject.VotingSystem.repositories.ResultsRepository;
import com.WebProject.VotingSystem.repositories.UserRepository;
import com.WebProject.VotingSystem.repositories.VotingHistoryRepository;
import com.WebProject.VotingSystem.repositories.VotingPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ResultsRepository resultsRepository;
    @Autowired
    VotingPeriodRepository votingPeriodRepository;
    @Autowired
    VotingHistoryRepository votingHistoryRepository;
    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(Integer CIN, String email, String password) throws EtAuthException {
        Pattern pattern =Pattern.compile("^(.+)@(.+)$");
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email format");
        Integer count1 = userRepository.getCountByCIN1(CIN);
        if (count1==0)
            throw new EtAuthException("your CIN is not registered, check with the local municipality!");
        Integer count2= userRepository.getCountByCIN2(CIN);
        if(count2>0)
            throw new EtAuthException("CIN used in other account!");
        Integer count3= userRepository.getCountByEmail(email);
        if (count3>0)
            throw new EtAuthException("email already taken!");

        Integer userID= userRepository.create(CIN ,email, password);
        return userRepository.findByID(userID );
    }

    @Override
    public Results fetchResultsByID(Integer VP_ID) throws EtResourceNotFoundException {
        VotingPeriod VP = votingPeriodRepository.findByID(VP_ID);
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String datetime = VP.getDatetime();
            Date creationDate= sdf.parse(datetime);
            Date now = new Date(System.currentTimeMillis());
            long difference = (now.getTime()- creationDate.getTime())/1000/60/60 ;
            if(difference<24)
                throw new EtResourceNotFoundException("Results are Not Out!");
        } catch (ParseException e) {e.printStackTrace();}

        return resultsRepository.findByID(VP_ID) ;
    }

    @Override
    public VotingHistory fetchHistoryByID(Integer userID, Integer VP_ID) throws EtResourceNotFoundException {
        return votingHistoryRepository.fetchHistoryByID(userID, VP_ID);
    }

    @Override
    public VotingHistory vote(Integer userID, Integer VP_ID, String vote) throws EtAuthException {
        Pattern pattern =Pattern.compile("PROP[1234]");
        if(!pattern.matcher(vote).matches())
            throw new EtAuthException("Invalid vote format");
        Integer count = votingHistoryRepository.getCountByID(userID, VP_ID);
        if (count>0)
            throw new EtAuthException("your have already voted!");
        VotingPeriod VP = votingPeriodRepository.findByID(VP_ID);
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String datetime = VP.getDatetime();
            Date creationDate= sdf.parse(datetime);
            Date now = new Date(System.currentTimeMillis());
            long difference = (now.getTime()- creationDate.getTime())/1000/60/60 ;
            if(difference>24)
                throw new EtAuthException("Voting Period is over!");
        } catch (ParseException e) {e.printStackTrace();}
         votingHistoryRepository.addVote(userID, VP_ID, vote);
         resultsRepository.addVote(VP_ID, vote);
        return votingHistoryRepository.fetchHistoryByID(userID, VP_ID);
    }
}

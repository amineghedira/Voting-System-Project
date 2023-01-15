package com.WebProject.VotingSystem.resources;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.domain.VotingHistory;
import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.services.ResidentService;
import com.WebProject.VotingSystem.services.UserService;
import com.WebProject.VotingSystem.services.VotingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
public class UserResource {
@Autowired
    VotingPeriodService votingPeriodService;
@Autowired
    UserService userService;
    @GetMapping("/VotingPeriods/")
    public ResponseEntity<List<VotingPeriod>> getAllVotingPeriods(HttpServletRequest request){
        List<VotingPeriod> votingPeriods = votingPeriodService.fetchAllVotingPeriods() ;
        return new ResponseEntity<>(votingPeriods, HttpStatus.OK);
    }
    @GetMapping("/vote/")
    public ResponseEntity<VotingPeriod> getLatestVotingPeriod(HttpServletRequest request){
        VotingPeriod votingPeriod = votingPeriodService.fetchLatestVotingPeriod() ;
        return new ResponseEntity<>(votingPeriod, HttpStatus.OK);
    }
    @PostMapping("/vote/")
    public ResponseEntity<VotingHistory> vote(HttpServletRequest request, @RequestBody Map<String, Object> voteMap){
        int userID = (Integer) request.getAttribute("userID");
        String vote = (String) voteMap.get("vote");
        VotingPeriod votingPeriod = votingPeriodService.fetchLatestVotingPeriod();
        Integer VP_ID= votingPeriod.getVP_ID();
        VotingHistory votingConfirmation = userService.vote(userID, VP_ID, vote );
        return new ResponseEntity<>(votingConfirmation, HttpStatus.CREATED);
    }
    @GetMapping("/VotingHistory/{VP_ID}")
    public ResponseEntity<VotingHistory> getVotingHistoryByID(HttpServletRequest request,
                                                    @PathVariable("VP_ID") Integer VP_ID){
        int userID = (Integer) request.getAttribute("userID");
        VotingHistory votingHistory = userService.fetchHistoryByID(userID, VP_ID);
        return new ResponseEntity<>(votingHistory, HttpStatus.OK);
    }
    @GetMapping("/VotingResults/{VP_ID}")
    public ResponseEntity<Results> getVotingResultsByID(HttpServletRequest request,
                                                        @PathVariable("VP_ID") Integer VP_ID) {
        Results results = userService.fetchResultsByID(VP_ID);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }





}

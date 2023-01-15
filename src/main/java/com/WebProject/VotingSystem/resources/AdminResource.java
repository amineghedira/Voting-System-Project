package com.WebProject.VotingSystem.resources;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.services.ResidentService;
import com.WebProject.VotingSystem.services.VotingPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
public class AdminResource {
@Autowired
ResidentService residentService;
@Autowired
VotingPeriodService votingPeriodService;
    @GetMapping("/residents/")
    public ResponseEntity<List<Resident>> getAllResidents(HttpServletRequest request){
        List<Resident> residents = residentService.fetchAllResidents() ;
        return new ResponseEntity<>(residents, HttpStatus.OK);
    }
    @PostMapping("/resident/")
    public ResponseEntity<Resident> addResident(HttpServletRequest request, @RequestBody Map<String, Object> residentMap){
        int CIN = Integer.valueOf((String) residentMap.get("CIN"));
        String fullName = (String) residentMap.get("fullName");
        String DOB = (String) residentMap.get("DOB");
        String gender = (String) residentMap.get("gender");
        Resident resident = residentService.addResident(CIN, fullName, DOB, gender);
        return new ResponseEntity<>(resident, HttpStatus.CREATED);
    }
    @GetMapping("/resident/{CIN}")
    public ResponseEntity<Resident> getResidentByID(HttpServletRequest request,
                                                    @PathVariable("CIN") Integer CIN){
       Resident resident = residentService.fetchResidentByID(CIN);
       return new ResponseEntity<>(resident, HttpStatus.OK);

    }
    @PutMapping("/resident/{CIN}")
    public ResponseEntity<Map<String, Boolean>> updateResident(HttpServletRequest request,
                                                               @PathVariable("CIN") Integer CIN,
                                                               @RequestBody Resident resident){
        residentService.updateResident(CIN, resident);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/VotingPeriod/")
    public ResponseEntity<VotingPeriod> addVotingPeriod(HttpServletRequest request, @RequestBody Map<String, Object> votingPeriodMap){
        String prop1 = (String) votingPeriodMap.get("prop1");
        String prop2 = (String) votingPeriodMap.get("prop2");
        String prop3 = (String) votingPeriodMap.get("prop3");
        String prop4 = (String) votingPeriodMap.get("prop4");
        VotingPeriod votingPeriod = votingPeriodService.addVotingPeriod(prop1, prop2, prop3, prop4);
        return new ResponseEntity<>(votingPeriod, HttpStatus.CREATED);
    }

    @DeleteMapping("/resident/{CIN}")
    public ResponseEntity<Map<String, Boolean>> deleteResident(HttpServletRequest request,
                                                               @PathVariable("CIN") Integer CIN){
        residentService.removeResidentWithAccount(CIN);
        Map<String, Boolean> map = new HashMap<>();
        map.put("resident and his account are successfully deleted", true);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

}

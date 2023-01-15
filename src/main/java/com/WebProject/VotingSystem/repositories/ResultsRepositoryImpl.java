package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ResultsRepositoryImpl implements ResultsRepository {


    private  static  final  String SQL_FIND_BY_ID = "SELECT VP_ID, PROP1, PROP2, PROP3, PROP4 FROM RESULTS WHERE VP_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO RESULTS(VP_ID, PROP1, PROP2, PROP3, PROP4) VALUES(?,?,?,?,?)";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Results findByID(Integer VP_ID) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{VP_ID}, resultsRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("could not Find!");
        }
    }

    @Override
    public void addVote(Integer VP_ID, String vote) throws EtBadRequestException {
          String SQL_ADD ="UPDATE RESULTS SET "+ vote + " = " + vote + " + 1 WHERE VP_ID= ?";
        try {
            jdbcTemplate.update(SQL_ADD, new Object[]{VP_ID});
        } catch (Exception e) {
            throw new EtBadRequestException("invalid request!");
        }
    }

    @Override
    public void create(Integer VP_ID, Integer prop1, Integer prop2, Integer prop3, Integer prop4) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_CREATE, new Object[]{VP_ID, prop1, prop2, prop3, prop4});
        } catch (Exception e) {
            throw new EtBadRequestException("invalid request!");
        }
     }
    private RowMapper<Results> resultsRowMapper = ((rs, rowNum) -> {
        return new Results(rs.getInt("VP_ID"),
                rs.getInt("PROP1"),
                rs.getInt("PROP2"),
                rs.getInt("PROP3"),
                rs.getInt("PROP4"));
    });
}

package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.domain.VotingPeriod;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
@Repository
public class VotingPeriodRepositoryImpl implements VotingPeriodRepository {
    private static final String SQL_FIND_ALL = "SELECT VP_ID, DATETIME, PROP1, PROP2, PROP3, PROP4 FROM VOTING_PERIOD";
    private  static  final  String SQL_FIND_BY_ID = "SELECT VP_ID, DATETIME, PROP1, PROP2, PROP3, PROP4 FROM VOTING_PERIOD WHERE VP_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO VOTING_PERIOD(VP_ID, DATETIME, PROP1, PROP2, PROP3, PROP4) VALUES(NEXTVAL('VP_ID_SEQ'),?,?,?,?,?)";
    private  static  final  String SQL_FIND_LATEST = "SELECT VP_ID, DATETIME, PROP1, PROP2, PROP3, PROP4 FROM VOTING_PERIOD WHERE VP_ID = (SELECT MAX(VP_ID) FROM VOTING_PERIOD)";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public VotingPeriod findLatest() throws EtResourceNotFoundException {
        return jdbcTemplate.queryForObject(SQL_FIND_LATEST, votingPeriodRowMapper);
    }

    @Override
    public List<VotingPeriod> findAll() throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, votingPeriodRowMapper);
    }

    @Override
    public VotingPeriod findByID(Integer VP_ID) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{VP_ID}, votingPeriodRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("Voting Period Not Found");
        }
    }

    @Override
    public Integer create(String datetime, String prop1, String prop2, String prop3, String prop4) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, datetime);
                ps.setString(2, prop1);
                ps.setString(3, prop2);
                ps.setString(4, prop3);
                ps.setString(5, prop4);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("VP_ID");
        }catch (Exception e){
            throw new EtBadRequestException("invalid request.Voting Period not created!");
        }
    }
    private RowMapper<VotingPeriod> votingPeriodRowMapper = ((rs, rowNum) -> {
        return new VotingPeriod(rs.getInt("VP_ID"),
                rs.getString("DATETIME"),
                rs.getString("PROP1"),
                rs.getString("PROP2"),
                rs.getString("PROP3"),
                rs.getString("PROP4"));
    });

}

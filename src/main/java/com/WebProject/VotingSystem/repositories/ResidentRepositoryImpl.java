package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.domain.Resident;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class ResidentRepositoryImpl implements ResidentRepository {
    private static final String SQL_FIND_ALL = "SELECT RESIDENTID, CIN, FULLNAME, DOB, GENDER FROM RESIDENT";
    private  static  final  String SQL_FIND_BY_ID = "SELECT RESIDENTID, CIN, FULLNAME, DOB, GENDER FROM RESIDENT WHERE CIN = ?";
    private static final String SQL_CREATE = "INSERT INTO RESIDENT(RESIDENTID, CIN, FULLNAME, DOB, GENDER) VALUES(NEXTVAL('RESIDENTID_SEQ'),?,?,?,?)";
    private static final String SQL_UPDATE ="UPDATE RESIDENT SET CIN = ?, FULLNAME = ?, DOB = ?, GENDER = ? WHERE CIN = ?";
    private static final String SQL_DELETE="DELETE FROM RESIDENT WHERE CIN = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Resident> findAll() throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, residentRowMapper);
    }

    @Override
    public Resident findByID(Integer CIN) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{CIN}, residentRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("Resident Not Found");
        }
    }

    @Override
    public Integer create(Integer CIN, String fullName, String DOB, String gender) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, CIN);
                ps.setString(2, fullName);
                ps.setString(3, DOB);
                ps.setString(4, gender);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("CIN");
        }catch (Exception e){
            throw new EtBadRequestException("invalid request.Resident not created!");
        }
    }

    @Override
    public void update(Integer CIN, Resident resident) throws EtBadRequestException {
        try{
            jdbcTemplate.update(SQL_UPDATE, new Object[]{resident.getCIN(), resident.getFullName(), resident.getDOB(), resident.getGender(), CIN});
            }
        catch (Exception e){
            throw new EtBadRequestException("invalid request.Resident not updated!");}

    }

    @Override
    public void removeByID(Integer CIN) {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{CIN});
        if (count==0)
            throw new EtResourceNotFoundException("resident not found!");
    }
    private RowMapper<Resident> residentRowMapper = ((rs, rowNum) -> {
        return new Resident(rs.getInt("RESIDENTID"),
                rs.getInt("CIN"),
                rs.getString("FULLNAME"),
                rs.getString("DOB"),
                rs.getString("GENDER"));
    });
}

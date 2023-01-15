package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.exceptions.EtAuthException;
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

@Repository
public class AdminRepositoryImpl implements  AdminRepository{
    private static final String SQL_CREATE="INSERT INTO ADMIN(ADMINID, ADMINNAME, PASSWORD) VALUES (NEXTVAL('ADMINID_SEQ'),?,?)";
    private static final String SQL_COUNT_BY_NAME="SELECT COUNT(*) FROM ADMIN WHERE ADMINNAME = ?" ;
    private static final String SQL_FIND_BY_NAME="SELECT ADMINID, ADMINNAME, PASSWORD FROM ADMIN WHERE ADMINNAME=?";
    private static final String SQL_FIND_BY_ID="SELECT ADMINID, ADMINNAME, PASSWORD FROM ADMIN WHERE ADMINID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String adminName, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, adminName);
                ps.setString(2, hashedPassword);
                return ps;
            }, keyHolder);
               return (Integer) keyHolder.getKeys().get("ADMINID");
        }catch (Exception e){
            throw new EtAuthException("invalid details.Account not created!");
        }
    }

    @Override
    public Admin findByNameAndPassword(String adminName, String password) throws EtAuthException {
        try{
            Admin admin = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, new Object[]{adminName}, adminRowMapper);
            if(!BCrypt.checkpw(password, admin.getPassword()))
                throw new EtAuthException("invalid name/password");
            return admin;
        }catch(EmptyResultDataAccessException e){
            throw new EtAuthException(("invalid name/password"));
        }
    }

    @Override
    public Integer getCountByName(String adminName) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_NAME, new Object[]{adminName}, Integer.class);
    }

    @Override
    public Admin findByID(Integer adminID) {
       return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{adminID}, adminRowMapper);
    }
    private RowMapper<Admin> adminRowMapper = ((rs, rowNum) -> {
         return new Admin(rs.getInt("ADMINID"),
                 rs.getString("ADMINNAME"),
                 rs.getString("PASSWORD"));
    });
}

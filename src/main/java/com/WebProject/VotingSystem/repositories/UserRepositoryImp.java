package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Admin;
import com.WebProject.VotingSystem.domain.User;
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
public class UserRepositoryImp implements UserRepository {

    private static final String SQL_CREATE= "INSERT INTO USERS(USERID, CIN, EMAIL, PASSWORD) VALUES (NEXTVAL('USERID_SEQ'), ?, ?, ?)";
    private static final String SQL_COUNT_BY_CIN1="SELECT COUNT(*) FROM RESIDENT WHERE CIN = ?";
    private static final String SQL_COUNT_BY_CIN2="SELECT COUNT(*) FROM USERS WHERE CIN = ?";
    private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID="SELECT USERID, CIN, EMAIL, PASSWORD FROM USERS WHERE USERID = ?";
    private static final  String SQL_FIND_BY_EMAIL="SELECT userid, CIN, EMAIL, PASSWORD FROM USERS WHERE EMAIL = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(Integer CIN, String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, CIN);
                ps.setString(2, email);
                ps.setString(3, hashedPassword);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USERID");
        }catch (Exception e){
            throw new EtAuthException("invalid details.Account not created!");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        try{
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new EtAuthException("invalid name/password");
            return user;
        }catch(EmptyResultDataAccessException e){
            throw new EtAuthException(("invalid name/password"));
        }
    }

    @Override
    public Integer getCountByCIN1(Integer CIN) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_CIN1, new Object[]{CIN}, Integer.class);
    }

    @Override
    public Integer getCountByCIN2(Integer CIN) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_CIN2, new Object[]{CIN}, Integer.class);
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public User findByID(Integer userID) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID}, userRowMapper);
    }
    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USERID"),
                rs.getInt("CIN"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"));
    });
}

package com.WebProject.VotingSystem.repositories;

import com.WebProject.VotingSystem.domain.Results;
import com.WebProject.VotingSystem.domain.VotingHistory;
import com.WebProject.VotingSystem.exceptions.EtBadRequestException;
import com.WebProject.VotingSystem.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VotingHistoryRepositoryImpl implements VotingHistoryRepository {
private static final String SQL_FIND_BY_ID ="SELECT USERID, VP_ID, VOTE FROM VOTING_HISTORY WHERE USERID = ? AND VP_ID = ?";
private static final String SQL_ADD="INSERT INTO VOTING_HISTORY(USERID, VP_ID, VOTE) VALUES (?,?,?)";
private static final String SQL_COUNT_BY_ID="SELECT COUNT(*) FROM VOTING_HISTORY WHERE USERID= ? AND VP_ID = ?";
@Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public VotingHistory fetchHistoryByID(Integer userID, Integer VP_ID) throws EtResourceNotFoundException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userID,VP_ID}, historyRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("could not Find!");
        }
    }

    @Override
    public Integer getCountByID(Integer userID, Integer VP_ID) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_ID, new Object[]{userID, VP_ID}, Integer.class);
    }

    @Override
    public void addVote(Integer userID, Integer VP_ID, String vote) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_ADD, new Object[]{userID,VP_ID,vote});
        } catch (Exception e) {
            throw new EtBadRequestException("invalid request!");
        }

    }

    private RowMapper<VotingHistory> historyRowMapper = ((rs, rowNum) -> {
        return new VotingHistory(rs.getInt("USERID"),
                rs.getInt("VP_ID"),
                rs.getString("VOTE"));
    });
}

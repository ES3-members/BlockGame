package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserScore;
@Repository
public class UserScoreDaoImpl implements UserScoreDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserScoreDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void insertUserScore(UserScore userscore) {
		jdbcTemplate.update("INSERT INTO UserScore(userName, score) VALUES(?, ?)",
				userscore.getUserName(), userscore.getScore());

	}

	@Override
	public List<UserScore> getAll() {
		// UserScoreテーブルのデータをすべて取り出し
		String sql = "SELECT userName, score FROM userscore";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<UserScore> list = new ArrayList<UserScore>();
		for(Map<String, Object> result : resultList) {
			UserScore userscore = new UserScore();
			userscore.setUserName((String)result.get("userName"));
			userscore.setScore((int)result.get("score"));
			list.add(userscore);
		}
		return list;
	}
	

}

package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.app.user.Ranking;
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
	
	@Override
	public boolean userExist(String userName) {
		// ユーザー名がUserテーブル（データベース）に存在するか確認
		// 存在すればTrue，存在しなければFalseを返す
		String sql = "SELECT userName FROM userscore";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<String> list = new ArrayList<String>();
		for(Map<String, Object> result : resultList) {
			String user = (String)result.get("userName");
			list.add(user);
		}
		return list.contains(userName);
	}
	
	@Override
	public int updateUserScore(UserScore userscore) {
		return jdbcTemplate.update("UPDATE userscore SET userName = ?, score = ? WHERE userName = ?",
				userscore.getUserName(), userscore.getScore(), userscore.getUserName());	
	}

	@Override
	public List<Ranking> getRanking() {
		// Get all record sorted by score
		String sql = "SELECT * FROM userscore ORDER BY score DESC";
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);
		List<Ranking> list = new ArrayList<Ranking>();
		int i = 1;
		for(Map<String, Object> result : resultList) {
			Ranking ranking = new Ranking();
			ranking.setPlace(i);
			ranking.setUserName((String)result.get("userName"));
			ranking.setScore((int)result.get("score"));
			list.add(ranking);
			i+=1;
		}
		return list;
	}

}
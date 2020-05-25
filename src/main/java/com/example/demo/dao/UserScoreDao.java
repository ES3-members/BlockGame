package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.UserScore;

public interface UserScoreDao {
	
	void insertUserScore(UserScore userscore);
	
	List<UserScore> getAll();
	
	boolean userExist(String userName);
	
	int updateUserScore(UserScore userscore);
	
	
}
package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.UserScore;

public interface UserScoreService {
	void save(UserScore userscore);
	
	List<UserScore> getAll();

	boolean userExist(String userName);
	
	void update(UserScore userscore);
	
}

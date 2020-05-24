package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserScoreDao;
import com.example.demo.entity.UserScore;

@Service
public class UserScoreServiceImpl implements UserScoreService {

	private final UserScoreDao dao;
	
	@Autowired
	public UserScoreServiceImpl(UserScoreDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(UserScore userscore) {
		dao.insertUserScore(userscore);
	}

	@Override
	public List<UserScore> getAll() {
		return dao.getAll();
	}
	
	@Override
	public boolean userExist(String userName) {
		return dao.userExist(userName);
	}
	
	@Override
	public void update(UserScore userscore) {
		dao.updateUserScore(userscore);
		
	}


}

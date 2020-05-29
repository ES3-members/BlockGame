package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao dao;
	
	@Autowired
	public UserServiceImpl(UserDao dao) {
		this.dao = dao;
	}
	
	@Override
	public void save(User user) {
		dao.insertUser(user);
	}
	
	@Override
	public void delete(User user) {
		dao.deleteUser(user);
	}

	@Override
	public List<User> getAll() {
		return dao.getAll();
	}

	@Override
	public boolean userExist(String userName) {
		return dao.userExist(userName);
	}

	@Override
	public boolean certificate(String userName, String password) {
		return dao.certificate(userName, password);
	}



}

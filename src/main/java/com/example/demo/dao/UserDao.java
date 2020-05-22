package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.User;

public interface UserDao {
	
	void insertUser(User user);
	
	List<User> getAll();
	
	boolean userExist(String userName);
	
	boolean certificate(String userName, String password);
	
	String hashedPass(String password);
}
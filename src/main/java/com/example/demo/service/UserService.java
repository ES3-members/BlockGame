package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.User;

public interface UserService {
	void save(User user);
	
	void delete(User user);
	
	List<User> getAll();
	
	boolean userExist(String userName);
	
	boolean certificate(String userName, String password);
	
	
}

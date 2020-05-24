package com.example.demo.entity;

public class UserScore {
	private String userName;
	private int score;
	public UserScore() {}
	public UserScore(String userName, int score) {
		super();
		this.userName = userName;
		this.score = score;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}

package com.example.demo.entity;

public class UserScore {
	private String userName;
	private int Score;
	public UserScore() {}
	public UserScore(String userName, int Score) {
		super();
		this.userName = userName;
		this.Score = Score;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int Score) {
		this.Score = Score;
	}
	
	
}

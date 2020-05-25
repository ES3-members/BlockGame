package com.example.demo.app.user;
public class Ranking {
	int place;
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	private String userName;
	private int score;
	public Ranking() {}
	public Ranking(int place, String userName, int score) {
		super();
		this.place = place;
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


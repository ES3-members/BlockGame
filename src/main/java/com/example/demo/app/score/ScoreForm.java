package com.example.demo.app.score;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ScoreForm {
	@NotNull()
	@Size(min = 1, max = 20, message="Please input 20 characters or less")
	private String userName;
	
	@NotNull()
	private int score;

	public ScoreForm() {}

	public ScoreForm(@NotNull @Size(min = 1, max = 20, message = "Please input 20 characters or less") String userName,
			@NotNull int score) {
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

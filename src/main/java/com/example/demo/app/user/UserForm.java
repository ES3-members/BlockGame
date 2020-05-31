package com.example.demo.app.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForm {
	@NotNull()
	@Size(min = 1, max = 20, message="Please input 20 characters or less")
	private String userName;
	
	@NotNull()
	private String password;
	
	private String passwordConfirm;
	
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public UserForm() {}

	public UserForm(String userName, String password, String passwordConfirm) {
		super();
		this.userName = userName;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

package com.capgemini.lmsjdbc.dto;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class UserPrimaryInfo implements Serializable {

	private int userId;
	private String userName;
	private String userEmailId;
	private String userPassword;
	private int noOfBooksBorrowed;
	private long mobileNo;
	private String role;
	private double fine;

	public UserPrimaryInfo() {
		super();
	}

	public UserPrimaryInfo(int userId, String userName, String userEmailId, String userPassword, int noOfBooksBorrowed,String role,
			double fine) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmailId = userEmailId;
		this.userPassword = userPassword;
		this.noOfBooksBorrowed = noOfBooksBorrowed;
		this.role = role;
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", userEmailId=" + userEmailId
				+ ", userPassword=" + userPassword + ", noOfBooksBorrowed=" + noOfBooksBorrowed + ", fine=" + fine
				+ "]";
	}

}

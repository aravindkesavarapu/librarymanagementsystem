package com.capgemini.lmscollections.service;

import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public interface UserService {
	boolean register(UserPrimaryInfo user,String role);

	boolean userAuthentication(String userEmailId, String userPassword,String role);

	boolean updatePassword(int userId, String oldPassword, String newPassword,String role);

}

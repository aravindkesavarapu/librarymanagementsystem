package com.capgemini.lmscollections.dao;

import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public interface UserDAO {
	boolean register(UserPrimaryInfo user,String role);

	boolean userAuthentication(String userEmailId, String userPassword,String role);

	boolean updatePassword(int userId, String oldPassword, String newPassword,String role);

}

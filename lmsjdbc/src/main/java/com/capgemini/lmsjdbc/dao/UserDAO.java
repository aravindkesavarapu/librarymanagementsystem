package com.capgemini.lmsjdbc.dao;

import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;

public interface UserDAO {
	boolean register(UserPrimaryInfo user,String userRole);

	boolean userAuthentication(int userId, String userPassword,String userRole);

	boolean updatePassword(int userId, String oldPassword, String newPassword,String userRole);

}

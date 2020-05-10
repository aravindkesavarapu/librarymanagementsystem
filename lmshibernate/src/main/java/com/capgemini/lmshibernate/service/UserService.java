package com.capgemini.lmshibernate.service;

import com.capgemini.lmshibernate.dto.UserPrimaryInfo;

public interface UserService {
	boolean register(UserPrimaryInfo user,String userRole);

	boolean userAuthentication(int id, String password,String userRole);

	boolean updatePassword(int id, String oldPassword, String newPassword,String userRole);

}

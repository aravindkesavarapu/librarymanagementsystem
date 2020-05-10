package com.capgemini.lmshibernate.dao;

import com.capgemini.lmshibernate.dto.UserPrimaryInfo;

public interface UserDAO {
	boolean register(UserPrimaryInfo user,String userRole);

	boolean userAuthentication(int id, String password,String userRole);

	boolean updatePassword(int id, String oldPassword, String newPassword,String userRole);

}

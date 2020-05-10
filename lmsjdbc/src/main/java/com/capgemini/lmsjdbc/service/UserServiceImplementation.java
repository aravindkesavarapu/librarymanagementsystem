package com.capgemini.lmsjdbc.service;

import com.capgemini.lmsjdbc.dao.UserDAO;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.factory.LibraryFactory;

public class UserServiceImplementation implements UserService {

	private UserDAO dao = LibraryFactory.getUserDAO(); 
	public boolean register(UserPrimaryInfo user,String userRole) {
		return dao.register(user,userRole);
	}

	public boolean userAuthentication(int userId, String userPassword,String userRole) {
		return dao.userAuthentication(userId, userPassword,userRole);
	}

	public boolean updatePassword(int userId, String oldPassword, String newPassword,String userRole) {
		return dao.updatePassword(userId, oldPassword, newPassword,userRole);
	}

}

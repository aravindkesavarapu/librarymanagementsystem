package com.capgemini.lmscollections.service;

import com.capgemini.lmscollections.dao.UserDAO;
import com.capgemini.lmscollections.dao.UserDAOImplementation;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public class UserServiceImplementation implements UserService {

	private UserDAO dao = new UserDAOImplementation();

	@Override
	public boolean register(UserPrimaryInfo user,String role) {
		return dao.register(user,role);
	}

	@Override
	public boolean userAuthentication(String userEmailId, String userPassword,String role) {
		return dao.userAuthentication(userEmailId, userPassword,role);
	}

	@Override
	public boolean updatePassword(int userId, String oldPassword, String newPassword,String role) {
		return dao.updatePassword(userId, oldPassword, newPassword,role);
	}

}

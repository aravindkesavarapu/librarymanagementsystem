package com.capgemini.lmshibernate.service;

import com.capgemini.lmshibernate.dao.UserDAO;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class UserServiceImplementation implements UserService {

	private UserDAO dao = LibraryFactory.getUserDAO();

	@Override
	public boolean register(UserPrimaryInfo userInfo, String userRole) {
		return dao.register(userInfo, userRole);
	}

	@Override
	public boolean userAuthentication(int id, String password, String userRole) {
		return dao.userAuthentication(id, password, userRole);
	}

	@Override
	public boolean updatePassword(int id, String oldPassword, String newPassword, String userRole) {
		return dao.updatePassword(id, oldPassword, newPassword, userRole);
	}

}

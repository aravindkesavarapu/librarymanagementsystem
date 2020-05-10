package com.capgemini.lmscollections.dao;

import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.exception.LMSException;

public class UserDAOImplementation implements UserDAO {

	@Override
	public boolean register(UserPrimaryInfo user, String role) {
		for (UserPrimaryInfo userBean : DataBase.USERDB) {
			if ((userBean.getUserId() == user.getUserId())
					|| (userBean.getUserEmailId().equals(user.getUserEmailId()))) {
				return false;
			}
		}

		DataBase.USERDB.add(user);
		return true;
	}

	@Override
	public boolean userAuthentication(String userEmailId, String userPassword, String role) {
		UserPrimaryInfo userInfo = null;
		for (UserPrimaryInfo userPrimaryInfo : DataBase.USERDB) {
			if (userPrimaryInfo.getUserEmailId().equalsIgnoreCase(userEmailId)) {
				userInfo = userPrimaryInfo;
			}
		}
		if (userInfo != null) {
			if ((userInfo.getUserEmailId().equalsIgnoreCase(userEmailId))
					&& (userInfo.getUserPassword().equals(userPassword))
					&& (userInfo.getRole().equalsIgnoreCase(role))) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new LMSException("User Mail Id Not Exists");
		}
	}

	@Override
	public boolean updatePassword(int userId, String oldPassword, String newPassword, String role) {
		for (UserPrimaryInfo userPrimaryInfo : DataBase.USERDB) {
			if ((userPrimaryInfo.getUserId() == userId) && (userPrimaryInfo.getUserPassword().equals(oldPassword))) {
				userPrimaryInfo.setUserPassword(newPassword);
				return true;
			}
		}
		return false;
	}
}

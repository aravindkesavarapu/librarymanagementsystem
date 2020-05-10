package com.capgemini.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.utility.JdbcUtility;
import com.capgemini.lmsjdbc.utility.QueryMapper;

public class UserDAOImplementation implements UserDAO {

	public boolean register(UserPrimaryInfo user, String userRole) {
		int count = 0;
		if (user.getUserEmailId() != null && !user.getUserEmailId().isEmpty()) {
			try (Connection connection = JdbcUtility.getConnection();
					PreparedStatement regUserStmt = connection.prepareStatement(QueryMapper.insertQuery);) {
				regUserStmt.setInt(1, user.getUserId());
				regUserStmt.setString(2, user.getUserName());
				regUserStmt.setString(3, user.getUserEmailId());
				regUserStmt.setString(4, user.getUserPassword());
				regUserStmt.setInt(5, user.getNoOfBooksBorrowed());
				regUserStmt.setString(6, user.getRole());
				regUserStmt.setDouble(7, 0);
				count = regUserStmt.executeUpdate();

				if (count == 0 && user.getUserEmailId().isEmpty()) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean userAuthentication(int userId, String userPassword, String userRole) {
		UserPrimaryInfo users = new UserPrimaryInfo();
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement statement = connection.prepareStatement(QueryMapper.userLogin)) {
			statement.setInt(1, userId);
			statement.setString(2, userPassword);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					users.setUserId(resultSet.getInt("id"));
					users.setUserPassword(resultSet.getString("password"));
					users.setUserEmailId(resultSet.getString("emailId"));
					users.setUserName(resultSet.getString("name"));
					users.setRole(resultSet.getString("role"));
					users.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
					return true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean updatePassword(int userId, String oldPassword, String newPassword, String userRole) {
		int noOfRowsAffected = 0;
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement setPasswordSt = connection.prepareStatement(QueryMapper.setPassword)) {
			System.out.println("userId" + userId);
			System.out.println("Old Password" + oldPassword);
			System.out.println("New Password" + newPassword);
			setPasswordSt.setString(1, newPassword);
			setPasswordSt.setString(2, oldPassword);
			setPasswordSt.setInt(3, userId);

			noOfRowsAffected = setPasswordSt.executeUpdate();
			if (noOfRowsAffected != 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}

	}
}

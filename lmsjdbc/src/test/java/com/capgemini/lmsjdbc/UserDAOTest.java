package com.capgemini.lmsjdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmsjdbc.dao.UserDAO;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.factory.LibraryFactory;

public class UserDAOTest {

	private UserDAO dao = LibraryFactory.getUserDAO();

	@Test
	public void testRegister() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setUserId(106);
		userInfo.setUserName("Aravindkk");
		userInfo.setUserEmailId("Aravindkk@gmail.com");
		userInfo.setUserPassword("User");
		boolean status = dao.register(userInfo, "Admin");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUserAuthentication() {

		boolean status = dao.userAuthentication(101, "google", "ADMIN");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUpdatePassword() {
		boolean status = dao.updatePassword(102, "google", "password", "ADMIN");
		Assertions.assertTrue(status);

	}

	@Test
	public void testRegister1() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setUserId(103);
		userInfo.setUserName("anil");
		userInfo.setUserEmailId("anil@gmail.com");
		userInfo.setUserPassword("anil");
		boolean status = dao.register(userInfo, "ADMIN");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUserAuthentication1() {
		boolean status = dao.userAuthentication(101, "aravind", "ADMIN");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUpdatePassword1() {
		boolean status = dao.updatePassword(101, "karavind", "anilnew", "STUDENT");
		Assertions.assertTrue(status);

	}

}

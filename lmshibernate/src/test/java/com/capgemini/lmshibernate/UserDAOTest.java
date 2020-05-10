package com.capgemini.lmshibernate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmshibernate.dao.UserDAO;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class UserDAOTest {
	UserDAO dao = LibraryFactory.getUserDAO();
	@Test
	public void testRegister() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setId(109);
		userInfo.setName("kesavarapuaravind");
		userInfo.setEmailId("aravaaind.kesa@gmail.com");
		userInfo.setPassword("User");
		boolean status = dao.register(userInfo, "Admin");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUserAuthentication() {

		boolean status = dao.userAuthentication(500, "karavind", "STUDENT");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUpdatePassword() {
		boolean status = dao.updatePassword(500, "karavind", "karavind", "STUDENT");
		Assertions.assertTrue(status);

	}
//false
	@Test
	public void testRegister1() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setId(109);
		userInfo.setName("kesavarapuaravind");
		userInfo.setEmailId("aravaiand.kesa@gmail.com");
		userInfo.setPassword("User");
		boolean status = dao.register(userInfo, "Admin");
		Assertions.assertFalse(status);
	}

	@Test
	public void testUserAuthentication1() {

		boolean status = dao.userAuthentication(500, "aravind", "ADMIN");
		Assertions.assertFalse(status);
	}

	@Test
	public void testUpdatePassword1() {
		boolean status = dao.updatePassword(500, "aravind", "karavind", "STUDENT");
		Assertions.assertFalse(status);

	}

}

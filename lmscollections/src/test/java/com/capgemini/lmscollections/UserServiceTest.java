package com.capgemini.lmscollections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;
import com.capgemini.lmscollections.service.UserService;

public class UserServiceTest {
	
	UserService service = LibraryFactory.getUserService();
	UserPrimaryInfo userInfo = new UserPrimaryInfo();
	@Test
	public void testRegister() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setUserId(106);
		userInfo.setUserName("ramesh");
		userInfo.setUserEmailId("ramesh@gmail.com");
		userInfo.setUserPassword("User");
		boolean status = service.register(userInfo, "Admin");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUserAuthentication() {

		boolean status = service.userAuthentication("google@gmail.com", "google", "ADMIN");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUpdatePassword() {
		boolean status = service.updatePassword(102, "google", "password", "ADMIN");
		Assertions.assertTrue(status);

	}

	@Test
	public void testRegister1() {
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		userInfo.setUserId(109);
		userInfo.setUserName("abdulkalam");
		userInfo.setUserEmailId("abdulkalam@gmail.com");
		userInfo.setUserPassword("User");
		boolean status = service.register(userInfo, "Admin");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUserAuthentication1() {

		boolean status = service.userAuthentication("google@gmail.com", "google", "ADMIN");
		Assertions.assertTrue(status);
	}

	@Test
	public void testUpdatePassword1() {
		boolean status = service.updatePassword(102, "google", "password", "ADMIN");	
		Assertions.assertTrue(status);

	}
	
}

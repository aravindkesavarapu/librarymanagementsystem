package com.capgemini.librarymanagementsystem_spring;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.lmsspringrest.dto.BooksBorrowed;
import com.capgemini.lmsspringrest.service.UserService;

public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testRequestBook() {
		boolean result = service.request(54321, 54321);
		Assertions.assertTrue(result);
	}
	@Test
	public void testReturnBook() {
		boolean result = service.returnBook(54321, 54321, "yes");
		Assertions.assertTrue(result);
	}
	@Test
	public void testBorrowedBook() {
		List<BooksBorrowed> info = service.borrowedBook(54321);
		Assertions.assertNotNull(info);
	}
	@Test
	public void testRequest1() {
		boolean result = service.request(54321, 54321);
		Assertions.assertNotNull(result);
	}
	@Test
	public void testReturnBooks() {
		boolean result = service.returnBook(54321, 54321, "yes");
		Assertions.assertNotNull(result);
	}
	@Test
	public void testBorrowedBook1() {
		List<BooksBorrowed> result = service.borrowedBook(54321);
		Assertions.assertNotNull(result);
	}
}

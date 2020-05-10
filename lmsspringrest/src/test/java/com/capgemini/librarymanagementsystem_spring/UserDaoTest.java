package com.capgemini.librarymanagementsystem_spring;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.lmsspringrest.dao.UserDao;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;

public class UserDaoTest {

	@Autowired
	private UserDao dao;
	
	@Test
	public void testRequestBook() {
		boolean result = dao.request(54321, 54321);
		Assertions.assertTrue(result);
	}
	@Test
	public void testReturnBook() {
		boolean result = dao.returnBook(54321, 54321, "yes");
		Assertions.assertTrue(result);
	}
	@Test
	public void testBorrowedBook() {
		List<BooksBorrowed> info = dao.borrowedBook(54321);
		Assertions.assertNotNull(info);
	}
	@Test
	public void testRequest1() {
		boolean result = dao.request(54321, 54321);
		Assertions.assertNotNull(result);
	}
	@Test
	public void testReturnBooks() {
		boolean result = dao.returnBook(54321, 54321, "yes");
		Assertions.assertNotNull(result);
	}
	@Test
	public void testBorrowedBook1() {
		List<BooksBorrowed> result = dao.borrowedBook(54321);
		Assertions.assertNotNull(result);
	}
	
}

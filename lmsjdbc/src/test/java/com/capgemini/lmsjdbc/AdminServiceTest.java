package com.capgemini.lmsjdbc;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.factory.LibraryFactory;
import com.capgemini.lmsjdbc.service.AdminService;

public class AdminServiceTest {
	AdminService service = LibraryFactory.getAdminService();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();
	
	@Test
	public void testAddBook() {
		bookInfo.setBookId(111);
		bookInfo.setBookAuthourName("Savi Sharma");
		bookInfo.setBookTitle("Every One Has A Story");
		bookInfo.setAvailable(true);
		boolean status = service.addBook(bookInfo);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIssue() {
		boolean status = service.issueBook(4);
		Assertions.assertTrue(status);
	}

	@Test
	public void testReceive() {
		boolean status = service.issueBook(111);
		Assertions.assertFalse(status);
	}

	@Test
	public void testDeleteBook() {
		boolean status = service.removeBook(100);
		Assertions.assertTrue(status);

	}

	@Test
	public void testDeleteBook1() {
		
		boolean status = service.removeBook(140);
		Assertions.assertNotNull(status);

	}
	
	@Test
	public void testShowBooks() {
		List<BookPrimaryInfo> list = service.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testShowUsers() {
		List<UserPrimaryInfo> list = service.getAllUsers();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testShowUsersEmpty() {
		List<UserPrimaryInfo> list = service.getAllUsers();
		Assertions.assertNotNull(list);
	}
}



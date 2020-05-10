package com.capgemini.lmshibernate;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmshibernate.dao.AdminDAO;
import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class AdminDAOTest {
	AdminDAO dao = LibraryFactory.getAdminDAO();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();
	
	@Test
	public void testAddBook() {
		bookInfo.setBookId(111);
		bookInfo.setBookAuthourName("Savi Sharma");
		bookInfo.setBookTitle("Every One Has A Story");
		bookInfo.setAvailable(true);
		boolean status = dao.addBook(bookInfo);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIssue() {
		boolean status = dao.issueBook(4);
		Assertions.assertTrue(status);
	}

	@Test
	public void testReceive() {
		boolean status = dao.issueBook(111);
		Assertions.assertFalse(status);
	}

	@Test
	public void testDeleteBook() {
		boolean status = dao.removeBook(100);
		Assertions.assertTrue(status);

	}

	@Test
	public void testDeleteBook1() {
		
		boolean status = dao.removeBook(140);
		Assertions.assertNotNull(status);

	}
	
	@Test
	public void testShowBooks() {
		List<BookPrimaryInfo> list = dao.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testShowUsers() {
		List<UserPrimaryInfo> list = dao.getAllUsers();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testShowUsersEmpty() {
		List<UserPrimaryInfo> list = dao.getAllUsers();
		Assertions.assertNotNull(list);
	}

}

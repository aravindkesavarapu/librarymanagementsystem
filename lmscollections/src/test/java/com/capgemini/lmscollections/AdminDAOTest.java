package com.capgemini.lmscollections;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmscollections.dao.AdminDAO;
import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;

public class AdminDAOTest {
	AdminDAO dao = LibraryFactory.getAdminDAO();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();

	@Test
	public void testAddBook() {
		bookInfo.setBookId(104);
		bookInfo.setBookAuthourName("Savi Sharma");
		bookInfo.setBookTitle("Every One Has A Story");
		bookInfo.setAvailable(true);
		boolean status = dao.addBook(bookInfo);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIssue() {
		DataBase.addToDB();
		boolean status = dao.bookIssue(101, 802);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIsBookReceived() {
		boolean status = dao.isBookReceived(101, 802);
		Assertions.assertTrue(status);

	}

	@Test
	public void testRemoveBook() {
		boolean status = dao.removeBook(104);
		Assertions.assertTrue(status);

	}

	@Test
	public void testGetAllBooks() {
		List<BookPrimaryInfo> list = dao.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testGetAllUsers() {
		List<UserPrimaryInfo> list = dao.getAllUsers();
		Assertions.assertNotNull(list);
	}

	// False
	@Test
	public void testAddBooks() {
		bookInfo.setBookId(104);
		bookInfo.setBookAuthourName("BenForta");
		bookInfo.setBookTitle("SQL");
		bookInfo.setAvailable(true);
		boolean status = dao.addBook(bookInfo);
		Assertions.assertFalse(status);
	}

	@Test
	public void testIssues() {
		boolean status = dao.bookIssue(101, 800);
		Assertions.assertFalse(status);
	}

	@Test
	public void testIsBookReceiveds() {
		boolean status = dao.isBookReceived(101, 121);
		Assertions.assertFalse(status);

	}

	@Test
	public void testRemoveBooks() {
		boolean status = dao.removeBook(898);
		Assertions.assertFalse(status);

	}

}

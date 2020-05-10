package com.capgemini.lmscollections;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;
import com.capgemini.lmscollections.service.AdminService;

public class AdminServiceTest {
	AdminService service = LibraryFactory.getAdminService();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();

	@Test
	public void testAddBook() {
		bookInfo.setBookId(104);
		bookInfo.setBookAuthourName("Savi Sharma");
		bookInfo.setBookTitle("Every One Has A Story");
		bookInfo.setAvailable(true);
		boolean status = service.addBook(bookInfo);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIssue() {
		DataBase.addToDB();
		boolean status = service.bookIssue(101, 802);
		Assertions.assertTrue(status);
	}

	@Test
	public void testIsBookReceived() {
		boolean status = service.isBookReceived(101, 802);
		Assertions.assertTrue(status);

	}

	@Test
	public void testRemoveBook() {
		boolean status = service.removeBook(104);
		Assertions.assertTrue(status);

	}

	@Test
	public void testGetAllBooks() {
		List<BookPrimaryInfo> list = service.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testGetAllUsers() {
		List<UserPrimaryInfo> list = service.getAllUsers();
		Assertions.assertNotNull(list);
	}

	// False
	@Test
	public void testAddBooks() {
		bookInfo.setBookId(104);
		bookInfo.setBookAuthourName("BenForta");
		bookInfo.setBookTitle("SQL");
		bookInfo.setAvailable(true);
		boolean status = service.addBook(bookInfo);
		Assertions.assertFalse(status);
	}

	@Test
	public void testIssues() {
		boolean status = service.bookIssue(101, 800);
		Assertions.assertFalse(status);
	}

	@Test
	public void testIsBookReceiveds() {
		boolean status = service.isBookReceived(101, 121);
		Assertions.assertFalse(status);

	}

	@Test
	public void testRemoveBooks() {
		boolean status = service.removeBook(898);
		Assertions.assertFalse(status);

	}

}

package com.capgemini.lmshibernate;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmshibernate.dao.StudentDAO;
import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class StudentDAOTest {
	StudentDAO dao = LibraryFactory.getStudentDAO();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();

	@Test
	public void testShowBooks() {
		List<BookPrimaryInfo> list = dao.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testBookRequest() {
		boolean status = dao.requestBook(101, 4);
		Assertions.assertTrue(status);
	}

	@Test
	public void testBookReturn() {
		boolean status = dao.requestBook(101, 2);
		Assertions.assertFalse(status);
	}

	@Test
	public void testSearchBook() {
		bookInfo.setBookId(101);
		List<BookPrimaryInfo> list = dao.searchBook(bookInfo);
		Assertions.assertNotNull(list);

	}

	@Test
	public void testShowBooks1() {
		List<BookPrimaryInfo> list = dao.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testBookRequest1() {
		boolean status = dao.requestBook(102, 102);
		Assertions.assertFalse(status);
	}

	@Test
	public void testBookReturn1() {
		boolean status = dao.returnBook(102, 102);
		Assertions.assertTrue(status);
	}

	@Test
	public void testSearchBook1() {
		bookInfo.setBookId(101);
		List<BookPrimaryInfo> list = dao.searchBook(bookInfo);
		Assertions.assertNotNull(list);

	}

}

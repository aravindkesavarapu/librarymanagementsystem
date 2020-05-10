package com.capgemini.lmshibernate;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;
import com.capgemini.lmshibernate.service.StudentService;

public class StudentServiceTest {
	StudentService service = LibraryFactory.getStudentService();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();

	@Test
	public void testShowBooks() {
		List<BookPrimaryInfo> list = service.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testBookRequest() {
		boolean status = service.requestBook(101, 4);
		Assertions.assertTrue(status);
	}

	@Test
	public void testBookReturn() {
		boolean status = service.requestBook(101, 2);
		Assertions.assertFalse(status);
	}

	@Test
	public void testSearchBook() {
		bookInfo.setBookId(101);
		List<BookPrimaryInfo> list = service.searchBook(bookInfo);
		Assertions.assertNotNull(list);

	}

	@Test
	public void testShowBooks1() {
		List<BookPrimaryInfo> list = service.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testBookRequest1() {
		boolean status = service.requestBook(102, 102);
		Assertions.assertFalse(status);
	}

	@Test
	public void testBookReturn1() {
		boolean status = service.returnBook(102, 102);
		Assertions.assertTrue(status);
	}

	@Test
	public void testSearchBook1() {
		bookInfo.setBookId(101);
		List<BookPrimaryInfo> list = service.searchBook(bookInfo);
		Assertions.assertNotNull(list);

	}
}
package com.capgemini.lmscollections;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.lmscollections.dao.StudentDAO;
import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;

public class StudentDAOTest {
	StudentDAO dao = LibraryFactory.getStudentDAO();
	BookPrimaryInfo bookInfo = new BookPrimaryInfo();

	
	@Test
	public void testGetAllBooks() {
		DataBase.addToDB();
		List<BookPrimaryInfo> list = dao.getAllBooks();
		Assertions.assertNotNull(list);
	}

	@Test
	public void testBookRequest() {
		DataBase.addToDB();
		boolean status = dao.bookRequest(101, 804);
		Assertions.assertTrue(status);
	}

	@Test
	public void testBookReturn() {
		boolean status = dao.bookReturn(102, 801);
		Assertions.assertTrue(status);
	}

	@Test
	public void testSearchBook() {
		List<BookPrimaryInfo> list = dao.searchBook(bookInfo);
		Assertions.assertNull(list);

	}

	//false
	@Test
	public void testBookReturn1() {
		boolean status = dao.bookReturn(101, 801);
		Assertions.assertFalse(status);
	}

	@Test
	public void testSearchBook1() {
		bookInfo.setBookId(111);
		List<BookPrimaryInfo> list = dao.searchBook(bookInfo);
		Assertions.assertNull(list);

	}

}

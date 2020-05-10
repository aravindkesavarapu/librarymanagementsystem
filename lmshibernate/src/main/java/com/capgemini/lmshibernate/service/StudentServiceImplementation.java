package com.capgemini.lmshibernate.service;

import java.util.List;

import com.capgemini.lmshibernate.dao.StudentDAO;
import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class StudentServiceImplementation implements StudentService {

	private StudentDAO dao = LibraryFactory.getStudentDAO();

	@Override
	public boolean requestBook(int userId, int bookId) {
		return dao.requestBook(userId, bookId);
	}

	@Override
	public boolean returnBook(int userId, int bookId) {
		return dao.returnBook(userId, bookId);
	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo) {
		return dao.searchBook(bookInfo);
	}

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		return dao.getAllBooks();
	}

}

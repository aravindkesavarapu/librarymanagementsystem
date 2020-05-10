package com.capgemini.lmsjdbc.service;

import java.util.List;

import com.capgemini.lmsjdbc.dao.StudentDAO;
import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.factory.LibraryFactory;

public class StudentServiceImplementation implements StudentService {

	private StudentDAO dao = LibraryFactory.getStudentDAO();

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		return dao.getAllBooks();
	}

	@Override
	public boolean requestBook(int userId, int bookId) {

		return dao.requestBook(userId, bookId);
	}

	@Override
	public boolean returnBook(int userId, int bookId) {
		return dao.returnBook(userId, bookId);
	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo) {
		return dao.searchBook(bookPrimaryInfo);
	}

}

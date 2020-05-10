package com.capgemini.lmscollections.service;

import java.util.List;

import com.capgemini.lmscollections.dao.StudentDAO;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;

public class StudentServiceImplementation implements StudentService {

	private StudentDAO dao = LibraryFactory.getStudentDAO();

	@Override
	public List<BookPrimaryInfo> getAllBooks(){
		return dao.getAllBooks();
	}

	
	@Override
	public boolean bookRequest(int userId, int bookId) {

		return dao.bookRequest(userId, bookId);
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		return dao.bookReturn(userId, bookId);
	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo) {
		return dao.searchBook(bookPrimaryInfo);
	}

}

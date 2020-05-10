package com.capgemini.lmsjdbc.dao;

import java.util.List;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;

public interface StudentDAO {
	
	List<BookPrimaryInfo> getAllBooks();

	boolean requestBook(int userId, int bookId);

	boolean returnBook(int userId, int bookId);

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo);

}

package com.capgemini.lmshibernate.service;

import java.util.List;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;

public interface StudentService {

	boolean requestBook(int userId, int bookId);

	boolean returnBook(int userId, int bookId);

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo);

	List<BookPrimaryInfo> getAllBooks();

}

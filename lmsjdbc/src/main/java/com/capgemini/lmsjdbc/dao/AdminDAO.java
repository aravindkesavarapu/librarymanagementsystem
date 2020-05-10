package com.capgemini.lmsjdbc.dao;

import java.util.List;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.RequestInfo;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;

public interface AdminDAO {

	boolean addBook(BookPrimaryInfo bookInfo);

	boolean issueBook(int requestId);

	boolean isBookReceived(int  requestedId);

	boolean removeBook(int bookId);

	List<BookPrimaryInfo> getAllBooks();

	List<UserPrimaryInfo> getAllUsers();

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo);

	List<RequestInfo> getAllRequests();

}

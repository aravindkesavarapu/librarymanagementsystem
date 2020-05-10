package com.capgemini.lmshibernate.service;

import java.util.List;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.RequestInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;

public interface AdminService {

	boolean addBook(BookPrimaryInfo book);

	boolean removeBook(int bookId);

	boolean issueBook(int requestId);

	boolean isBookReceived(int requestId);
	

	List<BookPrimaryInfo> getAllBooks();

	List<UserPrimaryInfo> getAllUsers();

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo);

	List<RequestInfo> getAllRequests();
}

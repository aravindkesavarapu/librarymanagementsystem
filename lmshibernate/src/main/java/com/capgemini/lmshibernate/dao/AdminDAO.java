package com.capgemini.lmshibernate.dao;

import java.util.List;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.RequestInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;

public interface AdminDAO {

	boolean addBook(BookPrimaryInfo book);

	boolean removeBook(int bookId);

	boolean issueBook(int requestId);

	boolean isBookReceived(int requestId);

	List<BookPrimaryInfo> getAllBooks();

	List<UserPrimaryInfo> getAllUsers();

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo);

	List<RequestInfo> getAllRequests();

}

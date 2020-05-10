package com.capgemini.lmscollections.dao;

import java.util.List;

import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public interface AdminDAO {

	boolean addBook(BookPrimaryInfo bookInfo);

	boolean bookIssue(int userId, int bookId);

	public boolean isBookReceived(int userId, int bookId);

	List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo);

	boolean removeBook(int bookId);

	List<BookPrimaryInfo> getAllBooks();

	List<UserPrimaryInfo> getAllUsers();

	List<RequestInfo> getAllRequests();

	List<RequestInfo> getAllIssuedBooks();

}

package com.capgemini.lmscollections.service;

import java.util.List;

import com.capgemini.lmscollections.dao.AdminDAO;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;

public class AdminServiceImplementation implements AdminService {

	private AdminDAO dao = LibraryFactory.getAdminDAO();

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		return dao.addBook(bookInfo);
	}

	@Override
	public boolean bookIssue(int userId, int bookId) {
		return dao.bookIssue(userId, bookId);
	}

	@Override
	public boolean isBookReceived(int userId, int bookId) {
		return dao.isBookReceived(userId, bookId);
	}

	@Override
	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo) {
		return dao.searchBook(bookPrimaryInfo);
	}

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		return dao.getAllBooks();
	}

	@Override
	public List<UserPrimaryInfo> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public List<RequestInfo> getAllRequests() {
		return dao.getAllRequests();
	}

	@Override
	public List<RequestInfo> getAllIssuedBooks() {
		return dao.getAllIssuedBooks();
	}

}

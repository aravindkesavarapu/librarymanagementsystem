package com.capgemini.lmsjdbc.service;

import java.util.List;

import com.capgemini.lmsjdbc.dao.AdminDAO;
import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.RequestInfo;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.factory.LibraryFactory;

public class AdminServiceImplementation implements AdminService {

	private AdminDAO dao = LibraryFactory.getAdminDAO();

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		return dao.addBook(bookInfo);
	}

	@Override
	public boolean issueBook(int requestId) {
		return dao.issueBook(requestId);
	}

	@Override
	public boolean isBookReceived(int requestedId) {
		return dao.isBookReceived(requestedId);
	}

	@Override
	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
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
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo) {
		return dao.searchBook(bookPrimaryInfo);
	}

	@Override
	public List<RequestInfo> getAllRequests() {
		return dao.getAllRequests();
	}

}

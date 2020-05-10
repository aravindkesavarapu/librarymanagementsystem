package com.capgemini.lmshibernate.service;

import java.util.List;

import com.capgemini.lmshibernate.dao.AdminDAO;
import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.RequestInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.factory.LibraryFactory;

public class AdminServiceImplementation implements AdminService {

	private AdminDAO dao = LibraryFactory.getAdminDAO();

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		return dao.addBook(bookInfo);
	}

	@Override
	public boolean removeBook(int bookId) {
		return dao.removeBook(bookId);
	}

	@Override
	public boolean issueBook(int requestId) {
		return dao.issueBook(requestId);
	}

	@Override
	public boolean isBookReceived(int requestId) {
		return dao.isBookReceived(requestId);
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
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo) {
		return dao.searchBook(bookInfo);
	}

	@Override
	public List<RequestInfo> getAllRequests() {
		return dao.getAllRequests();
	}

	

}

package com.capgemini.lmsspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.lmsspringrest.dao.AdminUserDao;
import com.capgemini.lmsspringrest.dao.AdminUserDaoImplements;
import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.User;

@Service
public class AdminUserServiceImplements implements AdminUserService{

	@Autowired
	private AdminUserDao dao;
	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		return dao.registerUser(user);
	}

	@Override
	public User authUser(String email, String password) {
		// TODO Auto-generated method stub
		return dao.authUser(email, password);
	}

	@Override
	public List<BookDetails> searchBookByTitle(String bookName) {
		// TODO Auto-generated method stub
		return dao.searchBookByTitle(bookName);
	}

	@Override
	public List<BookDetails> searchBookByAuthor(String authorName) {
		// TODO Auto-generated method stub
		return dao.searchBookByAuthor(authorName);
	}

	@Override
	public List<BookDetails> searchBookById(int bookId) {
		// TODO Auto-generated method stub
		return dao.searchBookById(bookId);
	}

	@Override
	public List<BookDetails> getBooksInfo() {
		// TODO Auto-generated method stub
		return dao.getBooksInfo();
	}

	@Override
	public boolean updatePassword(int id, String password, String newPassword, String role) {
		// TODO Auto-generated method stub
		return dao.updatePassword(id, password, newPassword, role);
	}

}

package com.capgemini.lmsspringrest.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.lmsspringrest.dao.UserDao;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;

@Service
public class UserServiceImplements implements UserService{

	@Autowired
	private UserDao dao; 

	@Override
	public boolean request(int bookId, int id) {
		// TODO Auto-generated method stub
		return dao.request(bookId, id);
	}

	@Override
	public boolean returnBook(int bookId, int id, String status) {
		// TODO Auto-generated method stub
		return dao.returnBook(bookId, id, status);
	}

	@Override
	public LinkedList<Integer> bookHistoryDetails(int id) {
		// TODO Auto-generated method stub
		return dao.bookHistoryDetails(id);
	}

	@Override
	public List<BooksBorrowed> borrowedBook(int id) {
		// TODO Auto-generated method stub
		return dao.borrowedBook(id);
	}


}

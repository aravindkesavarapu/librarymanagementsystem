package com.capgemini.lmsspringrest.dao;

import java.util.List;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.User;



public interface AdminUserDao {

	
	boolean registerUser(User user);

	User authUser(String email, String password);
	
	List<BookDetails> searchBookByTitle(String bookName);

	List<BookDetails> searchBookByAuthor(String authorName);

	List<BookDetails> searchBookById(int bookId);

	List<BookDetails> getBooksInfo();
	
	boolean updatePassword(int id, String password, String newPassword, String role);
}

package com.capgemini.lmsspringrest.dao;

import java.util.List;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;


public interface AdminDao {

	
	boolean addBook(BookDetails bookDetail);

	boolean removeBook(int bookId);

	boolean updateBook(BookDetails book);

	boolean bookIssue(int bookId, int id);
	
	List<RequestDetails> showRequests();

	List<BookIssue> showIssuedBooks();
	
	 List<User> showUsers();
}

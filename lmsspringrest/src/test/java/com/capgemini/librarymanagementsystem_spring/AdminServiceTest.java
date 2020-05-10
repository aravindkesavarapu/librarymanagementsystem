package com.capgemini.librarymanagementsystem_spring;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.service.AdminService;

public class AdminServiceTest {

	@Autowired
	private AdminService service;

	@Test
	public void testAddBook() {
		BookDetails bean = new BookDetails();
		bean.setBookId(12);
		bean.setBookName("jdbc");
		bean.setAuthorName("codd");
		bean.setPublisherName("sun");
		bean.setBookCategory("programming");
		bean.setCopies(3);
		boolean result = service.addBook(bean);
		Assertions.assertTrue(result);
	}
	
	@Test
	public void testAddBooks() {
		BookDetails bean = new BookDetails();
		bean.setBookId(12);
		bean.setBookName("jdbc");
		bean.setAuthorName("codd");
		bean.setPublisherName("sun");
		bean.setBookCategory("programming");
		bean.setCopies(3);
		boolean result = service.addBook(bean);
		Assertions.assertFalse(result);
	}
	
	@Test
	public void testRemoveBook() {
		
		boolean result = service.removeBook(12);
		Assertions.assertTrue(result);
	}
	
	
	@Test
	public void testRemoveBooks() {
		boolean result = service.removeBook(12);
		Assertions.assertFalse(result);
		
	}
	
	@Test
	public void testUpdateBook() {
		BookDetails book = new BookDetails();
		book.setBookId(12);
		book.setBookName("Sql");
		book.setAuthorName("Ben forta");
		book.setBookCategory("Programming");
		book.setCopies(4);
		book.setPublisherName("google");
		boolean result = service.updateBook(book);
		Assertions.assertTrue(result);
		
	}
	
	@Test
	public void testUpdateBooks() {
		
		BookDetails book = new BookDetails();
		book.setBookId(12);
		book.setBookName("Sql");
		book.setAuthorName("Ben forta");
		book.setBookCategory("Programming");
		book.setCopies(4);
		book.setPublisherName("google");
		boolean result = service.updateBook(book);
		Assertions.assertFalse(result);
	}
	
	@Test
	public void testBookIssue() {
		
		boolean result = service.bookIssue(12, 12);
		Assertions.assertTrue(result);
	}
	
	@Test
	public void testBookIssues() {
		boolean result = service.bookIssue(12, 12);
		Assertions.assertFalse(result);
	}
	
	@Test
	public void testShowRequest() {
		
		List<RequestDetails> result = service.showRequests();
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void testShowRequests() {
		List<RequestDetails> result = service.showRequests();
		Assertions.assertEquals(1, result.size());
		
	}
	
	@Test
	public void testShowUser() {
		List<User> result = service.showUsers();
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void testShowUsers() {
		List<User> result = service.showUsers();
		Assertions.assertEquals(1, result.size());
	}
	
	@Test
	public void testShowIssuedBook() {
		List<BookIssue> result = service.showIssuedBooks();
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void testShowIssuedBooks() {
		List<BookIssue> result = service.showIssuedBooks();
		Assertions.assertEquals(1, result.size());
		
	}
}

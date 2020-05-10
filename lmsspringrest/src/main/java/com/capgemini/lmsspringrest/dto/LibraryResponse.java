package com.capgemini.lmsspringrest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibraryResponse {
	
	private boolean error;
	private String message;
	
	private User user;
	private BookDetails bookDetails;
	private BookIssue bookIssue;
	private BooksBorrowed booksBorrowed;
	private RequestDetails requestDetails;
	
	private List<User> users;
	private List<BookDetails> book;
	private List<BookIssue> issue;
	private List<BooksBorrowed> borrowed;
	private List<RequestDetails> details;
	
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<BookDetails> getBook() {
		return book;
	}
	public void setBook(List<BookDetails> book) {
		this.book = book;
	}
	public List<BookIssue> getIssue() {
		return issue;
	}
	public void setIssue(List<BookIssue> issue) {
		this.issue = issue;
	}
	public List<BooksBorrowed> getBorrowed() {
		return borrowed;
	}
	public void setBorrowed(List<BooksBorrowed> borrowed) {
		this.borrowed = borrowed;
	}
	public List<RequestDetails> getDetails() {
		return details;
	}
	public void setDetails(List<RequestDetails> details) {
		this.details = details;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BookDetails getBookDetails() {
		return bookDetails;
	}
	public void setBookDetails(BookDetails bookDetails) {
		this.bookDetails = bookDetails;
	}
	public BookIssue getBookIssue() {
		return bookIssue;
	}
	public void setBookIssue(BookIssue bookIssue) {
		this.bookIssue = bookIssue;
	}
	public BooksBorrowed getBooksBorrowed() {
		return booksBorrowed;
	}
	public void setBooksBorrowed(BooksBorrowed booksBorrowed) {
		this.booksBorrowed = booksBorrowed;
	}
	public RequestDetails getRequestDetails() {
		return requestDetails;
	}
	public void setRequestDetails(RequestDetails requestDetails) {
		this.requestDetails = requestDetails;
	}
	
	

	
	
}

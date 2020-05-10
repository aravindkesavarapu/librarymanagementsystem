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


}

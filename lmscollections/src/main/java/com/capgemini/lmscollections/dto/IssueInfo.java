package com.capgemini.lmscollections.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class IssueInfo implements Serializable {

	private int userId;
	private String userName;
	private int bookId;
	private String bookAuthorName;
	private String bookName;
	private String category;
	private Date exceptedReturnDate;
	private Date returnDate;
	private String status;
}

package com.capgemini.lmscollections.dto;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class BookPrimaryInfo implements Serializable {
	private int bookId;
	private String bookTitle;
	private String bookAuthourName;
	private String bookCategory;
	private boolean isAvailable;

	public BookPrimaryInfo() {
		super();
	}

	public BookPrimaryInfo(int bookId, String bookTitle, String bookAuthourName, String bookCategory,
			boolean isAvailable) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookAuthourName = bookAuthourName;
		this.bookCategory = bookCategory;
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "BookInfo [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookAuthourName=" + bookAuthourName
				+ ", isAvailable" + isAvailable + " ]";
	}

}

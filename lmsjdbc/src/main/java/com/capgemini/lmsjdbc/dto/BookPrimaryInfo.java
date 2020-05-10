package com.capgemini.lmsjdbc.dto;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class BookPrimaryInfo implements Serializable {
	private int bookId;
	private String bookTitle;
	private String bookAuthourName;
	private boolean isAvailable;

	public BookPrimaryInfo() {
		super();
	}

	public BookPrimaryInfo(int bookId, String bookTitle, String bookAuthourName, boolean isAvailable) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookAuthourName = bookAuthourName;
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "BookInfo [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookAuthourName=" + bookAuthourName
				+ ", isAvailable" + isAvailable + " ]";
	}

}

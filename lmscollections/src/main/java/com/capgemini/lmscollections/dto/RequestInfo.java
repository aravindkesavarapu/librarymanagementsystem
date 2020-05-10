package com.capgemini.lmscollections.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class RequestInfo implements Serializable {

	private int bookId;
	private int userId;
	private boolean isIssued;
	private boolean isReturned;
	private Date issuedDate;
	private Date returnedDate;
	private Date expectedReturnedDate;
	
	public RequestInfo() {
		super();
	}

	public RequestInfo(int bookId, int userId,boolean isIssued,boolean isReturned,Date issuedDate,Date expectedReturnedDate,Date returnedDate) {
		super();
		this.bookId = bookId;
		this.userId = userId;
		this.isIssued = isIssued;
		this.isReturned = isReturned;
		this.issuedDate = issuedDate;
		this.expectedReturnedDate = expectedReturnedDate;
		this.returnedDate = returnedDate;
	}

}

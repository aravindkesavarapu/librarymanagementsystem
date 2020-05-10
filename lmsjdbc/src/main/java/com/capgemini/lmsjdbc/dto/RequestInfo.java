package com.capgemini.lmsjdbc.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class RequestInfo implements Serializable {

	private int requestId;
	private int bookId;
	private int userId;
	private Date issuedDate;
	private Date expectedReturnDate;
	private Date returnedDate;
	private double fine;
	private boolean isIssued;
}

package com.capgemini.lmshibernate.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "RequestInfo")
public class RequestInfo implements Serializable {

	@Id
	@Column
	@GeneratedValue
	private int requestId;
	@Column
	private int userId;
	@Column
	private int bookId;
	@Column
	private Date issuedDate;
	@Column
	private Date returnedDate;
	@Column
	private Date expectedReturnDate;
	@Column
	private boolean isIssued;

}

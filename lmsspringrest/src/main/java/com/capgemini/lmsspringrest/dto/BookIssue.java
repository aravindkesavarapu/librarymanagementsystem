package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "bookIssue")
public class BookIssue implements Serializable {

	@Id
	@Column
	@GeneratedValue
	private int id;
	@Column
	private int bookId;
	@Column
	private Date issueDate;
	@Column
	private Date returnDate;

}

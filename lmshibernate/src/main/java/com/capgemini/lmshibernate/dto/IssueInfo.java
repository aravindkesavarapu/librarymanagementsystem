package com.capgemini.lmshibernate.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "IssueInfo")
public class IssueInfo {
	@GeneratedValue
	@Id
	@Column
	private int sNo;
	@Column
	private int bookId;
	@Column
	private String bookName;
	@Column
	private String bookAuthorName;
	@Column
	private int userId;
	@Column
	private String userName;
	@Column
	private boolean isReturned;

}

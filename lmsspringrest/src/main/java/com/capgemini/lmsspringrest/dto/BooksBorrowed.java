package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "booksBorrowed")
public class BooksBorrowed implements Serializable {

	@Id
	@Column
	@GeneratedValue
	private int id;
	@Column
	private int uId;
	@Column
	private int bookId;
	@Column
	private String bookName;


}

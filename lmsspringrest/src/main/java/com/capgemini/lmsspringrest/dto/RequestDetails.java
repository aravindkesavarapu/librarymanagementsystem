package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "requestDetails")
@SequenceGenerator(name = "seq2", initialValue = 10001, allocationSize = 100)
public class RequestDetails implements Serializable {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq2")
	private int id;
	@Column
	private int uId;
	@Column
	private int bookId;
	@Column
	private String email;
	@Column
	private String bookName;

	}

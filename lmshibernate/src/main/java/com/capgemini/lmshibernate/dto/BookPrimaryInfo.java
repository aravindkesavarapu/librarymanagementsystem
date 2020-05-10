package com.capgemini.lmshibernate.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "bookPrimaryInfo")
public class BookPrimaryInfo implements Serializable {
	@Id
	private int bookId;
	@Column
	private String bookTitle;
	@Column
	private String bookAuthourName;
	@Column
	private String bookCategory;
	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean isAvailable;
}

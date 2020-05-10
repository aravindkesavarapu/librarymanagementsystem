package com.capgemini.lmsspringrest.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@SuppressWarnings("serial")
@Entity
@Table(name = "user")
@JsonRootName("User_Data")
@SequenceGenerator(name="seq4", initialValue=10001, allocationSize=100)
public class User implements Serializable {

	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq4")
	@JsonProperty("UserId")
	private int id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@Column
	private long mobileNo;
	@Column
	private String role;

}

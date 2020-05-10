package com.capgemini.lmscollections.validation;

import java.util.regex.Pattern;

import com.capgemini.lmscollections.exception.LMSException;

public class Validation {

	public boolean validateChoice(String userId) throws LMSException {
		String validating = "[0-9[10-11]]";
		boolean isValidated = Pattern.matches(validating, userId);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Choice should contain only digits");

		}
	}

	public boolean validateBookName(String bookDetails) throws LMSException{
		String bookRegx = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
		boolean isValidated = Pattern.matches(bookRegx, bookDetails);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Book Details should be alphabets and digits only ");
		}
	}

	
	public boolean validateBookCategory(String bookCategory) throws LMSException {
		String bookRegx = "[A-Z]{3}";
		boolean isValidated = Pattern.matches(bookRegx, bookCategory);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Book Category Should be 3 Letters and It Should be Capital letters");

		}
	}


	public boolean validateId(int id) throws LMSException {
		String idRegx = "[\\d&&[^0]][\\d]{2}";
		boolean isValidated = Pattern.matches(idRegx, String.valueOf(id));
		if (isValidated) {
			return true;
		} else {
			throw new LMSException(
					"Please Enter Valid Id Which Contains Exactly 3 Digits and first Digit should be a non zero digit");
		}
	}

	public boolean validateName(String name) throws LMSException {
		String nameRegEx = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
		boolean isValidated = Pattern.matches(nameRegEx, name);

		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Name should contains only Alpabates");
		}
	}

	public boolean validateEmail(String email) throws LMSException {
		String emailRegx = "[\\w&&[^_]]{3,50}[@]{1}\\D{2,50}[.]{1}\\D{2,50}";
		boolean isValidated = Pattern.matches(emailRegx, email);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Enter Proper EmailId, contains  @ and extensions(.com,.in,.org,..)");
		}
	}

	public boolean validatePassword(String password) throws LMSException {
		String passwordRegx = "^.{4,10}$";
		boolean isValidated = Pattern.matches(passwordRegx, password);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException("Enter Valid Password, Which Contains Minimum 4 charaters and Maximum 10");
		}
	}

	public boolean validateMobile(String inputMobile) throws LMSException {
		String mobileRegx = "[6789]{1}[\\d]{9}";
		boolean isValidated = Pattern.matches(mobileRegx, inputMobile);
		if (isValidated) {
			return true;
		} else {
			throw new LMSException(
					"Mobile number should start with 6 or 7 or 8 or 9 and must contains 10 digit number");
		}
	}
}
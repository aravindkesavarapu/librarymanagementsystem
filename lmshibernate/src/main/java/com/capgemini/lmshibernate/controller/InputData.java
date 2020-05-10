package com.capgemini.lmshibernate.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.capgemini.lmshibernate.exception.LMSException;
import com.capgemini.lmshibernate.validation.Validation;

public class InputData {

	static Scanner scanner =  new Scanner(System.in);
	static Validation VALIDATION = new Validation();
	public static int checkChoice() {
		boolean flag = false;
		int choice = 0;
		do {
			try {
				choice = scanner.nextInt();
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Invalid Choice, It Should Contails Only Digits");
				scanner.next();
			}
		} while (!flag);
		return choice;
	}

	public static String checkBookCategory() {
		String inputName = null;
		boolean flag = false;
		do {
			try {
				inputName = scanner.nextLine();
				if(!inputName.isEmpty()) {
					VALIDATION.validateBookName(inputName);
					}else {
					throw new LMSException(" ");
					}
						flag = true;
					
					} catch (LMSException e) {
					flag = false;
					System.err.println(e.getMessage());
				}
		} while (!flag);
		return inputName;
	}

	public static String checkBookName() {
		String inputName = null;
		boolean flag = false;
		do {
			try {
				inputName = scanner.nextLine();
				if(!inputName.isEmpty()) {
				VALIDATION.validateBookName(inputName);
				}else {
				throw new LMSException(" ");
				}
					flag = true;
				
				} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return inputName;
	}


	public static int checkRequest() {
		boolean flag = false;
		int choice = 0;
		do {
			try {
				choice = scanner.nextInt();
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Invalid Request, It Should Contails Only Digits");
				scanner.next();
			}
		} while (!flag);
		return choice;
	}

	public static int checkId() {
		boolean flag = false;
		int id = 0;
		do {
			try {
				id = scanner.nextInt();
				VALIDATION.validateId(id);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Id Should contains only Digits");
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);

		return id;
	}

	public static String checkName() {
		String name = null;
		boolean flag = false;
		do {
			try {
				name = scanner.nextLine();
				if(!name.isEmpty()) {
					VALIDATION.validateName(name);
					}else {
					throw new LMSException(" ");
					}
					flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		// return userName.toLowerCase();
		return name;

	}

	public static String checkEmailId() {
		String emailId = null;
		boolean flag = false;
		do {
			try {
				emailId = scanner.next();
				VALIDATION.validateEmail(emailId);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return emailId;
	}

	public static String checkPassword() {
		String password = null;
		boolean flag = false;
		do {
			try {
				password = scanner.next();
				VALIDATION.validatePassword(password);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return password;
	}

	public static Double checkPrice() {
		boolean flag = false;
		double price = 0;
		do {
			try {
				price = scanner.nextInt();
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Price should contails only digits ");
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return price;
	}

	public static boolean checkAvailability() {
		boolean isAvail = false;
		boolean flag = false;
		do {
			try {
				isAvail = scanner.nextBoolean();
				flag = true;
			} catch (InputMismatchException e) {
				System.err.println("Enter Boolean value true/false");
				flag = false;
				scanner.next();
			}
		} while (!flag);
		return isAvail;
	}

	public static String checkRole() {
		String role = null;
		boolean flag = false;

		do {
			role = scanner.next();
			if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("user")) {
				flag = true;
			} else {
				flag = false;
				System.err.println("Role Must Be Admin Or User");
			}
		} while (!flag);

		return role.toLowerCase();
	}

}

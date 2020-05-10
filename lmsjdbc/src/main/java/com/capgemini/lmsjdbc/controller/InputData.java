package com.capgemini.lmsjdbc.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.capgemini.lmsjdbc.exception.LMSException;
import com.capgemini.lmsjdbc.validation.Validation;

public class InputData {
	static Validation VALIDATION = new Validation();
	static Scanner scanner = new Scanner(System.in);

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

	public static int checkChoice() {
		String inputChoice = null;
		boolean flag = false;
		int choice = 0;
		do {
			try {
				inputChoice = scanner.next();
				VALIDATION.validateChoice(inputChoice);
				flag = true;
				choice = Integer.parseInt(inputChoice);
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Invalid Choice, It should contails only digits");
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return choice;
	}

	public static String checkBookName() {
		String inputName = null;
		boolean flag = false;
		do {
			try {
				inputName = scanner.nextLine();
				if (!inputName.isEmpty()) {
					VALIDATION.validateBookName(inputName);
				} else {
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
				System.err.println("Invalid Id,It should contails only digits");
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);

		return id;
	}

	public static String checkName() {
		String inputName = null;
		boolean flag = false;
		do {
			try {
				inputName = scanner.next();
				VALIDATION.validateName(inputName);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return inputName;
	}

	public static String checkEmailId() {
		String inputEmailId = null;
		boolean flag = false;
		do {
			try {
				inputEmailId = scanner.next();
				VALIDATION.validateEmail(inputEmailId);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return inputEmailId.toLowerCase();
	}

	public static String checkPassword() {
		String inputPassword = null;
		boolean flag = false;
		do {
			try {
				inputPassword = scanner.next();
				VALIDATION.validatePassword(inputPassword);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);

		return inputPassword;
	}

	public static long checkMobileNo() {
		String inputMobileNo = null;
		long mobileNo = 0;
		boolean flag = false;
		do {
			try {
				inputMobileNo = scanner.next();
				VALIDATION.validateMobile(inputMobileNo);
				mobileNo = Long.parseLong(inputMobileNo);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Mobile Number should contains only numbers");
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return mobileNo;

	}

//	private static Double checkPrice() {
//		boolean flag = false;
//		double price = 0;
//		do {
//			try {
//				price = scanner.nextInt();
//				flag = true;
//			} catch (InputMismatchException e) {
//				flag = false;
//				System.err.println("Price should contails only digits ");
//				scanner.next();
//			}
//		} while (!flag);
//		return price;
//	}

}

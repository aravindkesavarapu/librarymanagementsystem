package com.capgemini.lmshibernate.controller;

public class LibraryController {
	public static void main(String[] args) {
		System.out.println("************* Welcome To Library *************");
		loginForm();
	}

	public static void loginForm() {
		boolean isLoggedOut = false;
		do {
			System.out.println("Press 1 to Admin Login");
			System.out.println("Press 2 to User Login");
			System.out.println("Press 3 to Exit");
			System.out.println("Enter your choice");
			int choice = InputData.checkChoice();
			switch (choice) {

			case 1:

				System.out.println("Welcome Admin LogIn Page");
				System.out.println("---------------------------");
				isLoggedOut = Admin.adminOperations();
				if (isLoggedOut == true) {
					System.out.println("SucessFully Logged Out");
					System.out.println("Press 1 to Go UserPage");
					System.out.println("Press 2 to Exit");
					int checkUser = InputData.checkChoice();
					if (checkUser == 1) {
						loginForm();
					} else if (checkUser == 2) {
						exit();
					} else {
						System.out.println("Please Enter Valid Choice");
					}
				}
				break;

			case 2:
				System.out.println("Welcome Student LogIn Page");
				System.out.println("---------------------------");
				isLoggedOut = Student.studentOperations();
				if (isLoggedOut == true) {
					System.out.println("SucessFully Logged Out");
					System.out.println("Press 1 to Go UserPage");
					System.out.println("Press 2 to Exit");
					int checkUser = InputData.checkChoice();
					if (checkUser == 1) {
						loginForm();
					} else if (checkUser == 2) {
						exit();
					} else {
						System.out.println("Please Enter Valid Choice");
					}
				}

			case 3:
				exit();
				break;
			default:
				System.out.println("Please Enter 1 to 3");
				loginForm();
				break;
			}

		} while (!isLoggedOut);
	}

	public static void exit() {
		System.out.println("Thank You");
	}

}

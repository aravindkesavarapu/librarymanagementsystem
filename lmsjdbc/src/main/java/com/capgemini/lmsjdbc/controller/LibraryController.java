package com.capgemini.lmsjdbc.controller;

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
				if(isLoggedOut==true) {
					System.out.println("SucessFully Logged Out");
				}
				break;

			case 2:
				System.out.println("Welcome Student LogIn Page");
				System.out.println("---------------------------");
				isLoggedOut = Student.studentOperations();
				if(isLoggedOut==true) {
					System.out.println("SucessFully Logged Out");
				}
				
			case 3:
				System.out.println("Thank You");
				break;
			default:
				System.out.println("Choose 1 or 2");
				loginForm();
				break;
			}

		} while (!isLoggedOut);
	}

}

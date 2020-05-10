package com.capgemini.lmshibernate.controller;

import java.util.List;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.exception.LMSException;
import com.capgemini.lmshibernate.factory.LibraryFactory;
import com.capgemini.lmshibernate.service.StudentService;
import com.capgemini.lmshibernate.service.UserService;

public class Student {
	public static boolean studentOperations() {
		boolean isLoggedIn = false;
		boolean isSearch = false;
		try {
			StudentService studentService = LibraryFactory.getStudentService();
			UserService userService = LibraryFactory.getUserService();
			boolean flag = false;
			int userChoice = 0;
			int updateUserId = 0;
			System.out.println("User Log In Page");
			System.out.println("-----------------");
			System.out.println("Enter User id: ");
			int userId = InputData.checkId();
			System.out.println("Enter User password: ");
			String password = InputData.checkPassword();
			isLoggedIn = userService.userAuthentication(userId, password, "Student");

			if (isLoggedIn) {
				try {
					System.out.println("User logged in");
					System.out.println("-------------------");
					do {

						System.out.println("Press 1 to Student Register");
						System.out.println("Press 2 Books in Library");
						System.out.println("Press 3 Search a Book");
						System.out.println("Press 4 Request a Book");
						System.out.println("Press 5 Return Book");
						System.out.println("Press 6 Change Password");
						System.out.println("Press 0 Exit");
						System.out.println("Enter your choice");
						userChoice = InputData.checkChoice();

						switch (userChoice) {
						case 1:
							System.out.println("***Welcome To User Registration Page***");
							System.out.println("Enter User Registration Details");
							int userId1 = (int) (Math.random() * 1000);
							if (userId1 <= 100) {
								userId1 = userId1 + 100;
							}
							System.out.println("User Id : " + userId1);
							System.out.println("Enter user name : ");
							String name = InputData.checkName();
							System.out.println("Enter User Mail Id :");
							String mailId = InputData.checkEmailId();
							System.out.println("Enter Password :");
							password = InputData.checkPassword();

							UserPrimaryInfo userPrimaryInfo1 = new UserPrimaryInfo();
							userPrimaryInfo1.setId(userId);
							userPrimaryInfo1.setName(name);
							userPrimaryInfo1.setEmailId(mailId);
							userPrimaryInfo1.setPassword(password);
							userPrimaryInfo1.setRole("STUDENT");

							try {
								boolean isRegisterd = userService.register(userPrimaryInfo1, "STUDENT");
								if (isRegisterd)
									System.out.println("User Registered Succesfully And UserId : " + userId);
								else {
									System.out.println("User Not Registered");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							System.out.println("***Welcome To All Books In library***");
							System.out.println("---------------------------------------");
							List<BookPrimaryInfo> allBooks = null;

							try {
								allBooks = studentService.getAllBooks();
								if (allBooks != null) {
									int count = 0;
									System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNo", "BOOK ID",
											"BOOK NAME", "AUTHOUR", "AVAILABILITY"));

									for (BookPrimaryInfo book : allBooks) {
										++count;
										System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", count,
												book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
												book.isAvailable()));
									}
									System.out.println();
								} else {
									System.out.println("No Books Are Not Found In Library");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}

							break;

						case 3:
							isSearch = booksSearch();
							break;

						case 4:
							System.out.println("**Welcome To Request Book Page***");
							System.out.println("----------------------------------");
							System.out.println("Enter user id : ");
							boolean isRequest = false;
							do {
								int requestUserId = InputData.checkId();
								if (requestUserId == userId) {
									flag = true;
								} else {
									flag = false;
									System.err.println("Enter Your User Id");
								}
							} while (!flag);

							System.out.println("Enter book id : ");
							int bookId = InputData.checkId();

							try {
								isRequest = studentService.requestBook(userId, bookId);
								if (isRequest)
									System.out.println("Request placed to Admin Succesfully");
								else {
									System.out.println("Book Not Requested");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 5:
							System.out.println("***Welcome To Return Book Page***");
							System.out.println("----------------------------------");
							System.out.println("Enter User Id : ");
							boolean isReturned = false;
							do {
								int returnUserId = InputData.checkId();
								if (returnUserId == userId) {
									flag = true;
								} else {
									flag = false;
									System.err.println("Enter Your User Id : ");
								}
							} while (!flag);

							System.out.println("Enter Book Id : ");
							bookId = InputData.checkId();

							try {
								isReturned = studentService.returnBook(userId, bookId);
								if (isReturned)
									System.out.println("Book is Returning to Admin Successfully");
								else {
									System.out.println("Book is Not Returned to Admin ");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:
							System.out.println("***Welcomr to Change Password Page***");
							System.out.println("Enter User id : ");
							boolean isUpdated = false;
							do {
								updateUserId = InputData.checkId();
								if (updateUserId == userId) {
									flag = true;
								} else {
									flag = false;
									System.err.println("Enter Your User Id");
								}
							} while (!flag);
							System.out.println("Enter User password: ");
							String oldPassword = InputData.checkPassword();
							System.out.println("Enter New Password: ");
							String newPassword = InputData.checkPassword();

							try {

								isUpdated = userService.updatePassword(updateUserId, oldPassword, newPassword,
										"Student");
								if (isUpdated) {
									System.out.println("Password Changed Successfully");
								} else {
									System.out.println("Password Not Changed Due To Invalid Credentialsa");
								}
							} catch (Exception e) {
								System.err.println(e.getMessage());
							}
							break;

						case 0:
							isLoggedIn = false;
							break;

						default:
							System.out.println("Invalid Choice, It Should be in between 0-5");
							break;
						}
					} while (userChoice != 0 || isSearch || isLoggedIn);// user while ends

				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.out.println("Invalid User Credentials");
				System.out.println("Please Re-Enter Login Credinetials");
				studentOperations();
			}
		} catch (LMSException e) {

			System.err.println(e.getMessage());
			System.out.println("Please Re-Enter Login Credinetials");
			studentOperations();
		}
		return isLoggedIn;
	}

	public static boolean booksSearch() {
		StudentService studentService = LibraryFactory.getStudentService();
		int searchBookBy;
		int bookId;
		String bookTitle;
		boolean flag = false;
		String authourName;
		System.out.println("Welcome to Seach Book Page");
		System.out.println("---------------------------");
		do {
			System.out.println("Press 1 to Search Book By Id");
			System.out.println("Press 2 to Search Book By Book Name");
			System.out.println("Press 3 to Search Book By Authour Name");
			System.out.println("Press 4 to Search Book By Category");
			System.out.println("Press 5 to Search Exit");
			System.out.println("Press 0 to Logout");
			System.out.println("Enter Your Choice");
			searchBookBy = InputData.checkChoice();
			BookPrimaryInfo bookInfo = new BookPrimaryInfo();

			switch (searchBookBy) {
			case 1:
				System.out.println("Enter Book Id :");
				bookId = InputData.checkId();
				bookInfo.setBookId(bookId);
				List<BookPrimaryInfo> books = null;
				try {
					books = studentService.searchBook(bookInfo);
					if (books != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(), book.getBookCategory(),
									book.isAvailable()));
						}
						System.out.println("-------------------------------------------------------------------");
						System.out.println();
					} else {

						System.out.println("No books are available with this Book Id : " + bookId);

					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 2:
				System.out.println();
				System.out.println("Enter Book Title to Search");
				bookTitle = InputData.checkBookName();
				bookInfo.setBookTitle(bookTitle);
				List<BookPrimaryInfo> bookSearch = null;
				try {
					bookSearch = studentService.searchBook(bookInfo);
					if (bookSearch != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : bookSearch) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(), book.getBookCategory(),
									book.isAvailable()));
						}
						System.out.println("-----------------------------------------------------------------");
						System.out.println();
					} else {
						System.out.println("No books are available with this Book Name : " + bookTitle);

					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 3:
				System.out.println();
				System.out.println("Enter Book Authour Name to Search");
				authourName = InputData.checkBookName();
				bookInfo.setBookAuthourName(authourName);
				List<BookPrimaryInfo> bookAuthor = null;
				try {

					bookAuthor = studentService.searchBook(bookInfo);
					if (bookAuthor != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : bookAuthor) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(), book.getBookCategory(),
									book.isAvailable()));
						}
						System.out.println("-------------------------------");
						System.out.println();
					} else {
						System.out.println("No books are available with this Book Author Name : " + authourName);

					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println();
				System.out.println("Enter Book Category Name to Search");
				String categoryName = InputData.checkBookCategory();
				bookInfo.setBookCategory(categoryName);
				List<BookPrimaryInfo> bookCategory = null;

				try {

					bookCategory = studentService.searchBook(bookInfo);
					if (bookCategory != null) {
						int i = 1;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : bookCategory) {
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(), book.getBookCategory(),
									book.isAvailable()));
							i++;
						}
						System.out.println("-------------------------------------------------------------------");
						System.out.println();
					} else {
						System.out.println("No books are available with this Book Category Name : " + categoryName);

					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;
			case 5:
				System.out.println("");
				flag = true;
				searchBookBy = 0;
				break;
			case 0:
				LibraryController.loginForm();
				break;

			default:
				System.out.println("Invali Choice, Choice Must Be In Between 1 to 4");
				break;
			}

		} while (searchBookBy != 0);
		return flag;

	}
}

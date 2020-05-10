package com.capgemini.lmscollections.controller;

import java.util.List;

import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.exception.LMSException;
import com.capgemini.lmscollections.factory.LibraryFactory;
import com.capgemini.lmscollections.service.StudentService;
import com.capgemini.lmscollections.service.UserService;

public class Student {
	public static boolean studentOperations() {
		boolean isLoggedIn = false;
		try {
			UserService userService = LibraryFactory.getUserService();
			StudentService studentService = LibraryFactory.getStudentService();
			boolean isSearch = false;
			System.out.println("Enter User Email id");
			String userEmailId = InputData.checkEmailId();
			System.out.println("Enter User Password");
			String userPassword = InputData.checkPassword();
			int userChoice;
			isLoggedIn = userService.userAuthentication(userEmailId, userPassword, "Student");
			if (isLoggedIn) {
				try {
					System.out.println("User logged in");
					System.out.println("--------------------");

					do {
						System.out.println("Press 1 to Student Register");
						System.out.println("Press 2 Books in Library");
						System.out.println("Press 3 Search a Book");
						System.out.println("Press 4 Request a Book");
						System.out.println("Press 5 Return a Book");
						System.out.println("Press 6 Change Password");
						System.out.println("Press 0 Log Out");
						System.out.println("Enter your choice");
						userChoice = InputData.checkChoice();

						switch (userChoice) {

						case 1:
							System.out.println("***Welcome to User Registration Details***");
							System.out.println("-------------------------------------------");
							boolean isRegistered = false;
							int userId = (int) (Math.random() * 1000);
							if (userId <= 100) {
								userId = userId + 100;
							}
							System.out.println("User Id : " + userId);
							System.out.println("Enter User name");
							String name = InputData.checkName();
							System.out.println("Enter User Email Id");
							String emailId = InputData.checkEmailId();
							System.out.println("Enter User password");
							String password = InputData.checkPassword();

							UserPrimaryInfo userInfo = new UserPrimaryInfo();
							userInfo.setUserId(userId);
							userInfo.setUserName(name);
							userInfo.setUserEmailId(emailId);
							userInfo.setUserPassword(password);
							userInfo.setRole("Student");

							try {
								isRegistered = userService.register(userInfo, "ADMIN");
								if (isRegistered) {
									System.out.println("User Registered Sucessfully");
								} else {
									System.out.println("User All Ready Registered");
									isRegistered = false;
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							try {
								System.out.println("Books present in library are :");
								System.out.println("-------------------------------");
								List<BookPrimaryInfo> allBooks = studentService.getAllBooks();
								if (allBooks != null) {
									System.out.println(String.format("%-10s %-35s %-25s %-10s", "BOOK ID", "BOOK NAME",
											"AUTHOUR", "AVAILABILITY"));

									for (BookPrimaryInfo book : allBooks) {

										System.out.println(String.format("%-10s %-35s %-25s %-10s", book.getBookId(),
												book.getBookTitle(), book.getBookAuthourName(), book.isAvailable()));
									}
								} else {
									System.out.println("No Books In Library");
								}

							} catch (LMSException e) {
								System.err.println(e.getMessage());
							} catch (Exception e) {
								System.err.println("Books Not Found");
							}
							break;

						case 3:
							isSearch = booksSearch();
							break;

						case 4:
							System.out.println("Requesting a Book");
							System.out.println("-----------------------");
							System.out.println("Enter User Id");
							int reqUserId = InputData.checkId();
							System.out.println("Enter Book Id");
							int bookId = InputData.checkId();

							try {

								boolean isRequested = studentService.bookRequest(reqUserId, bookId);
								if (isRequested) {
									System.out.println("Request Placed to Admin Sucessfully");
								} else {
									System.out.println("Invalid Request");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 5:
							System.out.println("Returning a book:");
							System.out.println("---------------------");
							System.out.println("Enter User Id");
							int returnUserId = InputData.checkId();
							System.out.println("Enter Book Id");
							int returnbookId = InputData.checkId();

							try {

								boolean isReturned = studentService.bookReturn(returnUserId, returnbookId);
								if (isReturned) {
									System.out.println("Book is Returning to Admin");
								} else {
									System.out.println("Invalid Return");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:
							System.out.println("update Password");
							System.out.println("-------------------");
							System.out.println("Enter User Id");
							int updateuserId = InputData.checkId();
							System.out.println("Enter Old Password");
							String oldPassword = InputData.checkPassword();
							System.out.println("Enter New Password");
							String newPassword = InputData.checkPassword();

							try {
								boolean isUpdated = userService.updatePassword(updateuserId, oldPassword, newPassword,
										"Student");
								if (isUpdated) {
									System.out.println("Password Changed Sucessfully");
								} else {
									System.out.println("Password Can't be Changed Due To Invalid Credentials");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 0:
							isLoggedIn = true;
							break;

						default:
							System.out.println("Invalid Choice, Choice Must be in Between 0 to 6");
							break;
						}
					} while (userChoice != 0 || isSearch);// user while ends
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

				try {
					List<BookPrimaryInfo> books = studentService.searchBook(bookInfo);
					if (books != null) {
						int countBooks = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {
							++countBooks;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", countBooks,
									book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
									book.getBookCategory(), book.isAvailable()));

						}
						System.out.println("-----------------------------------------------------------------------");
						System.out.println();
					} else {
						System.out.println("No books are available with this Book Id : "+bookId);
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

				try {
					List<BookPrimaryInfo> books = studentService.searchBook(bookInfo);
					int countBooks = 0;
					if (books != null) {
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {
							++countBooks;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", countBooks,
									book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
									book.getBookCategory(), book.isAvailable()));
						}
						System.out.println("-------------------------------");
						System.out.println();
					} else {
						System.out.println("No Books Found With this Title : " + bookTitle);
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

				try {

					List<BookPrimaryInfo> books = studentService.searchBook(bookInfo);
					int countBooks = 0;
					if (books != null) {
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {
							++countBooks;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", countBooks,
									book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
									book.getBookCategory(), book.isAvailable()));

						}
						System.out.println(
								"----------------------------------------------------------------------------");
						System.out.println();
					} else {
						System.out.println("No Books Found With this Author Name : " + authourName);
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

				try {

					List<BookPrimaryInfo> books = studentService.searchBook(bookInfo);
					if (books != null) {
						int countBooks = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-5s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "CATEGORY", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {

							++countBooks;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-5s %-10s", countBooks,
									book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
									book.getBookCategory(), book.isAvailable()));
						}
						System.out.println("--------------------------------------------");
						System.out.println();
					} else {
						System.out.println("No Books Found With this Category : " + categoryName);
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
				System.out.println("Invali Choice, Choice Must Be In Between 0 to 5");
				break;
			}

		} while (searchBookBy != 0);
		return flag;

	}
}

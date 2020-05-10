package com.capgemini.lmscollections.controller;

import java.util.List;

import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.exception.LMSException;
import com.capgemini.lmscollections.factory.LibraryFactory;
import com.capgemini.lmscollections.service.AdminService;
import com.capgemini.lmscollections.service.UserService;

public class Admin {

	public static boolean adminOperations() {
		boolean isLoggedIn = false;
		try {
			AdminService adminService = LibraryFactory.getAdminService();
			UserService userService = LibraryFactory.getUserService();
			boolean isSearch = false;
			System.out.println("Enter Admin Email id");
			String userEmailId = InputData.checkEmailId();
			System.out.println("Enter Admin password");
			String userPassword = InputData.checkPassword();
			int check = 0;
			isLoggedIn = userService.userAuthentication(userEmailId, userPassword, "ADMIN");
			if (isLoggedIn == true) {

				try {
					System.out.println("Admin logged in");
					System.out.println("---------------------------");

					do {
						System.out.println("Press 1 to Register");
						System.out.println("Press 2 to Add Book");
						System.out.println("Press 3 to Remove Book");
						System.out.println("Press 4 to Search Books");
						System.out.println("Press 5 to Book Issue");
						System.out.println("Press 6 to Receive Returned Book");
						System.out.println("Press 7 to View All Requests");
						System.out.println("Press 8 to view All Issued Books");
						System.out.println("Press 9 to View All Books");
						System.out.println("Press 10 to View All Users");
						System.out.println("Press 11 Change Password");
						System.out.println("Press 0 to Log Out");
						System.out.println("Enter your choice");

						check = InputData.checkChoice();
						switch (check) {
						case 1:
							System.out.println("Enter User Registration Details");
							System.out.println("---------------------------------");
							System.out.println("Press 1 to Admin Registration");
							System.out.println("Press 2 to Student Registration");
							String role = null;
							int userChoice = InputData.checkChoice();
							if (userChoice == 1) {
								role = "ADMIN";
							} else {
								role = "STUDENT";
							}
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
							userInfo.setRole(role);

							try {
								isRegistered = userService.register(userInfo, "ADMIN");
								if (isRegistered) {
									System.out.println("User Registered Sucessfully and User Id :" + userId);
								} else {
									System.out.println("User All Ready Exists");
									isRegistered = false;
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							System.out.println("Welcome to Add Book Page ");
							System.out.println("---------------------------");
							boolean isAvail = false;
							int bookId = (int) (Math.random() * 1000);
							if (bookId <= 100) {
								bookId = bookId + 100;
							}
							System.out.println("Book Id : " + bookId);
							System.out.println("Enter Book Title : ");
							String bookTitle = InputData.checkBookName();
							System.out.println("Enter Book Authour Name : ");
							String bookName = InputData.checkBookName();
							System.out.println("Enter Book Category :");
							String bookCategory = InputData.checkBookCategory();
							System.out.println("Is Book Available for Borrowing: ");
							System.out.println("Press 1 to Book is Available for borrow ");
							System.out.println("Press 2 to Book is  Available for Reference ");
							int bookChoice1 = InputData.checkChoice();
							do {
								if (bookChoice1 == 1) {
									isAvail = true;
								} else if (bookChoice1 == 2) {
									isAvail = false;
								}
							} while (bookChoice1 != 1 && bookChoice1 != 2);

							BookPrimaryInfo bookInfo = new BookPrimaryInfo();
							bookInfo.setBookId(bookId);
							bookInfo.setBookAuthourName(bookName);
							bookInfo.setBookTitle(bookTitle);
							bookInfo.setAvailable(isAvail);
							bookInfo.setBookCategory(bookCategory);

							try {

								boolean isAdded = adminService.addBook(bookInfo);
								if (isAdded) {
									System.out.println("Book is a Added Sucessfully");
								} else {
									System.out.println("Cannot Add Book, As Book Id Already Exists");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 3:
							System.out.println("Welcome to Remove Book Page");
							System.out.println("----------------------------");
							bookInfo = new BookPrimaryInfo();
							System.out.println("Enter Book Id To Remove");
							bookId = InputData.checkId();
							bookInfo.setBookId(bookId);
							try {
								boolean isRemoved = adminService.removeBook(bookId);
								if (isRemoved) {
									System.out.println("Book Removed Sucessfully");
								} else {
									System.out.println("Cannot Remove The Book, As Book Id Not Found");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:
							isSearch = booksSearch();

							break;
						case 5:

							System.out.println("Welcome to Book Issue page");
							System.out.println("----------------------------");
							System.out.println("Enter User Id :");
							userId = InputData.checkId();
							System.out.println("Enter Book Id :");
							bookId = InputData.checkId();

							try {
								boolean isIssued = adminService.bookIssue(userId, bookId);
								if (isIssued) {
									System.out.println("Book Issued Sucessfully");
								} else {
									System.out.println(
											"Book Cannot be Issued, as Student Exceeds maximum Borrowing limit");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:

							System.out.println("Welcome to Recieve Returned Book Page ");
							System.out.println("-----------------------------------------");
							System.out.println("Enter User Id");
							userId = InputData.checkId();
							System.out.println("Enter Book Id");
							bookId = InputData.checkId();

							try {

								boolean isReceived = adminService.isBookReceived(userId, bookId);
								if (isReceived)
									System.out.println(" Received Returned book Sucessfully");
								else {
									System.out.println("Book Cannot be Received, as Invalid UserId/BookId");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 7:
							try {
								System.out.println("Welcome to Requests Page");
								List<RequestInfo> requestInfos = adminService.getAllRequests();
								if (requestInfos != null) {
									System.out.println("Number of Book Requests :" + requestInfos.size());
									int noOfRequests = 0;
									System.out.println(String.format("%-5s %-10s %-10s %-15s ", "SNO.", "USER ID",
											"BOOK ID", "IS ISSUED"));

									for (RequestInfo info : requestInfos) {
										if (info.isIssued()) {
											++noOfRequests;
											System.out.println(String.format("%-5s %-10s %-10s %-15s", noOfRequests,
													info.getUserId(), info.getBookId(), info.isIssued()));
										}
									}
									System.out.println(
											"---------------------------------------------------------------------------------");
									System.out.println("Total No. of Requests" + noOfRequests);
									System.out.println();
								} else {
									System.out.println("No Requests Present in Library");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 8:
							System.out.println("Welcome to View All Issue Books");
							System.out.println("--------------------------------");
							List<RequestInfo> requestInfos = adminService.getAllRequests();

							if (requestInfos != null) {
								int count = 0;
								System.out.println(String.format("%-5s %-10s %-10s %-15s %-15s %-15s %-25s %-15s",
										"SNO.", "USER ID", "BOOK ID", "IS ISSUED", "IS RETURNED", "ISSUED DATE",
										"EXPECTED RETURNED DATE", "RETURNED DATE"));

								for (RequestInfo info : requestInfos) {
									++count;
									if (info.isIssued()) {
										System.out
												.println(String.format("%-5s %-10s %-10s %-15s %-15s %-15s %-25s %-15s",
														count, info.getUserId(), info.getBookId(), info.isIssued(),
														info.isReturned(), info.getIssuedDate(),
														info.getExpectedReturnedDate(), info.getReturnedDate()));

									}
								}
							} else {
								System.out.println("Books Are Not Issued Till Now");
							}

							break;
						case 9:

							try {
								System.out.println("Welcome to All Book Details Page");
								List<BookPrimaryInfo> allBooks = adminService.getAllBooks();
								int bookCount = 0;
								if (allBooks != null) {
									System.out.println("Books present in library are :");
									System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO.", "BOOK ID",
											"BOOK NAME", "AUTHOUR", "AVAILABILITY"));

									for (BookPrimaryInfo book : allBooks) {
										++bookCount;
										System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s ", bookCount,
												book.getBookId(), book.getBookTitle(), book.getBookAuthourName(),
												book.isAvailable()));

									}
									System.out.println("Total Number Of Books Present In Library : " + allBooks.size());
								} else {
									System.out.println("No Books Found In Library");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							} catch (Exception e) {
								System.out.println("Books Not Found");
							}
							break;
						case 10:
							try {
								System.out.println("Welcome to all Users Page");
								List<UserPrimaryInfo> userInfos = adminService.getAllUsers();
								if (userInfos != null) {
									System.out.println("Users of Library are :");
									System.out.println("------------------------------------------");
									int adminCount = 1;
									int studentCount = 1;
									for (UserPrimaryInfo userInfo1 : userInfos) {
										if (userInfo1.getRole().equalsIgnoreCase("admin")) {
											System.out.println(String.format("%-5s %-10s %-20s %-30s ", "SNO.",
													"USER ID", "NAME", "EMAIL ID"));
											for (UserPrimaryInfo info : userInfos) {

												System.out.println(String.format("%-5s %-10s %-20s %-30s", adminCount,
														info.getUserId(), info.getUserName(), info.getUserEmailId()));
												adminCount++;
											}

										}
									}

									System.out.println(String.format("%-5s %-10s %-20s %-30s %-25s %-5s", "SNO.",
											"USER ID", "NAME", "EMAIL ID", "NO OF BOOKS BORROWED", "FINE"));
									System.out.println();
									System.out.println(
											"----------------------------------------------------------------------------------------------");
									for (UserPrimaryInfo info : userInfos) {
										if (info.getRole().equalsIgnoreCase("student")) {
											System.out.println(
													String.format("%-5s %-10s %-20s %-30s %-25s %-5s", studentCount,
															info.getUserId(), info.getUserName(), info.getUserEmailId(),
															info.getNoOfBooksBorrowed(), info.getFine()));
											studentCount++;
										}
									}
									System.out.println("Total No. of User : " + userInfos.size());
									System.out.println("Total No. of Admin User : " + adminCount);
									System.out.println("Total No. of Admin User : " + studentCount);
								} else {
									System.out.println("No Users Found");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;
						case 11:
							System.out.println("***Welcome to Update Password Page***");
							System.out.println("--------------------------------------");
							System.out.println("Enter User Id :");
							int updateuserId = InputData.checkId();

							System.out.println("Enter Old Password");
							String oldPassword = InputData.checkPassword();
							System.out.println("Enter New Password");
							String newPassword = InputData.checkPassword();

							try {
								boolean isUpdated = userService.updatePassword(updateuserId, oldPassword, newPassword,
										"Admin");
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
							check = 0;
							break;

						default:
							System.err.println("Choice Should Be in Between 0 to 11");
							break;

						}
					} while (check != 0 || isSearch); // do while for Admin options // Admin activities end
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.out.println("Invalid User Credentials");
			}
		} catch (LMSException e) {
			System.err.println(e.getMessage());
			System.out.println("Please Re-Enter Login Credinetials");
			adminOperations();
		}
		return isLoggedIn;
	}

	public static boolean booksSearch() {
			AdminService adminService = LibraryFactory.getAdminService();
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
					List<BookPrimaryInfo> books = adminService.searchBook(bookInfo);
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
						System.out.println("-------------------------------");
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

				try {
					List<BookPrimaryInfo> books = adminService.searchBook(bookInfo);
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

					List<BookPrimaryInfo> books = adminService.searchBook(bookInfo);
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

					List<BookPrimaryInfo> books = adminService.searchBook(bookInfo);
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

package com.capgemini.lmsjdbc.controller;

import java.util.List;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.RequestInfo;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.exception.LMSException;
import com.capgemini.lmsjdbc.factory.LibraryFactory;
import com.capgemini.lmsjdbc.service.AdminService;
import com.capgemini.lmsjdbc.service.UserService;

public class Admin {
	public static boolean adminOperations() {
		boolean isLoggedIn = false;
		try {
			UserService userService = LibraryFactory.getUserService();
			AdminService adminService = LibraryFactory.getAdminService();
			int check = 0;
			boolean bookSearch = false;
			System.out.println("Enter Admin id : ");
			int id = InputData.checkId();
			System.out.println("Enter Admin password : ");
			String password = InputData.checkPassword();
			isLoggedIn = userService.userAuthentication(id, password, "ADMIN");
			if (isLoggedIn) {

				try {
					System.out.println("Admin logged in");
					System.out.println("--------------------");
					do {
						System.out.println("Press 1 to Register");
						System.out.println("Press 2 to Add Book");
						System.out.println("Press 3 to Remove Book");
						System.out.println("Press 4 to Search Books");
						System.out.println("Press 5 to Book Issue");
						System.out.println("Press 6 to Receive Returned Book");
						System.out.println("Press 7 to View All Requests");
						System.out.println("Press 8 to View All Books");
						System.out.println("Press 9 to View All Users");
						System.out.println("Press 0 to Exit");
						System.out.println("Enter your choice");
						check = InputData.checkChoice();

						switch (check) {
						case 1:
							System.out.println("***Welcome To User Registration Page***");
							System.out.println("Enter User Registration Details");
							System.out.println("Press 1 to Admin Registration");
							System.out.println("Press 2 to Student Registration");
							int roleChoice = InputData.checkChoice();
							String role = null;
							if (roleChoice == 1) {
								role = "ADMIN";
							} else if (roleChoice == 2) {
								role = "STUDENT";
							}
							int userId = (int) (Math.random() * 1000);
							if (userId <= 100) {
								userId = userId + 100;
							}
							System.out.println("User Id : " + userId);
							System.out.println("Enter user name : ");
							String name = InputData.checkName();
							System.out.println("Enter User Email Id :");
							String emailId = InputData.checkEmailId();
							System.out.println("Enter Password :");
							password = InputData.checkPassword();

							UserPrimaryInfo userPrimaryInfo = new UserPrimaryInfo();
							userPrimaryInfo.setUserId(userId);
							userPrimaryInfo.setUserName(name);
							userPrimaryInfo.setUserEmailId(emailId);
							userPrimaryInfo.setUserPassword(password);
							userPrimaryInfo.setRole(role);

							try {
								boolean isRegisterd = userService.register(userPrimaryInfo, role);
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
							System.out.println("***Welcome To Add Book Page***");
							System.out.println("--------------------------------");
							System.out.println("Enter book Id : ");
							boolean isAvail = false;
							int bookId = InputData.checkId();
							System.out.println("Enter book Book Title : ");
							String bookTitle = InputData.checkName();
							System.out.println("Enter Authour Name : ");
							String authourName = InputData.checkName();
							System.out.println("Is Book Available for Borrowing");
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
							bookInfo.setBookAuthourName(authourName);
							bookInfo.setBookTitle(bookTitle);
							bookInfo.setAvailable(isAvail);

							try {

								boolean isAdded = adminService.addBook(bookInfo);
								if (isAdded) {
									System.out.println("Book is added Sucessfully");
								} else {
									System.out.println("Book Already Exists");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 3:
							System.out.println("***Welcome To Delete Book Page***");
							System.out.println("------------------------------------");
							bookInfo = new BookPrimaryInfo();
							System.out.println("Enter Book Id : ");
							bookId = InputData.checkId();
							bookInfo.setBookId(bookId);

							try {
								boolean isRemoved = adminService.removeBook(bookId);
								if (isRemoved) {
									System.out.println("Book Removed Successfully");
								} else {
									System.out.println("Book Can't Be Removed,No Book Found With Given Id");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:
							bookSearch = booksSearch();
							break;

						case 5:
							System.out.println("***Welcome To Issue Book Page***");
							System.out.println("----------------------------------");
							System.out.println("Enter Request Id : ");
							int issueId = InputData.checkRequest();

							try {
								boolean isIssued = adminService.issueBook(issueId);
								if (isIssued) {
									System.out.println("Book Issued ");
								} else {
									System.out.println("Book Not Issued");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:

							System.out.println("***Welcome To Received Book Page***");
							System.out.println("-----------------------------------");
							System.out.println("Enter Request Id");
							int requestId = InputData.checkRequest();

							try {

								boolean isreceived = adminService.isBookReceived(requestId);
								if (isreceived)
									System.out.println(" Received Returned book Sucessfully");
								else {
									System.out.println("No Request are Received with this Id : " + requestId);
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 7:
							System.out.println("***Welcome To All Request Book Page***");
							System.out.println("----------------------------------------");
							List<RequestInfo> requestInfos = null;
							try {
								requestInfos = adminService.getAllRequests();
								if (requestInfos != null) {
									if (requestInfos.size() != 0) {
										System.out.println(String.format("%-5s %-15s %-15s %-15s  ", "SNO.",
												"REQUEST ID", "USER ID", "BOOK ID"));
										int count = 0;
										for (RequestInfo info : requestInfos) {
											++count;
											System.out.println(String.format("%-5s %-15s %-15s %-15s ", count,
													info.getRequestId(), info.getUserId(), info.getBookId()));
										}
										System.out.println();
									}
								} else {
									System.out.println("No Requests Are Found");
									System.out.println();
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

									case 8:
							System.out.println("***Welcome To View All Books Page***");
							System.out.println("-------------------------------------");
							List<BookPrimaryInfo> allBooks = null;
							try {
								allBooks = adminService.getAllBooks();
								if (allBooks != null) {
									int availableCount = 0;
									int notAvailableCount = 0;
									if (allBooks.size() != 0) {
										System.out.println("Available Books In Library: ");
										System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO",
												"BOOK ID", "BOOK NAME", "AUTHOUR", "AVAILABILITY"));

										for (BookPrimaryInfo book : allBooks) {
											if (book.isAvailable()) {
												++availableCount;

												System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s",
														availableCount, book.getBookId(), book.getBookTitle(),
														book.getBookAuthourName(), book.isAvailable()));
											}
										}

										System.out.println("Issued Books In Library");
										System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO",
												"BOOK ID", "BOOK NAME", "AUTHOUR", "AVAILABILITY"));

										for (BookPrimaryInfo book : allBooks) {
											if (book.isAvailable() == false) {
												++notAvailableCount;
												System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s",
														availableCount, book.getBookId(), book.getBookTitle(),
														book.getBookAuthourName(), book.isAvailable()));
											}
										}
									}
									System.out.println("Total No. Of Available Books : " + availableCount);
									System.out.println();

									System.out.println("Total No. Of Available Books : " + notAvailableCount);
									System.out.println("Total No. Of Books : " + allBooks.size());
									System.out.println();
								} else {
									System.out.println("No Books Are Issued");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 9:

							System.out.println("***Welcome To All Users Page***");
							System.out.println("-------------------------------");
							List<UserPrimaryInfo> users = null;
							try {
								users = adminService.getAllUsers();
								if (users != null) {
									int adminCount = 0;
									int studentCount = 0;
									System.out.println(String.format("%-5s %-10s %-20s %-30s %-5s", "SNO.", "USER ID",
											"NAME", "EMAIL ID", "ROLE"));
									System.out.println("Admin Users");
									for (UserPrimaryInfo info : users) {
										if (info.getRole().equalsIgnoreCase("ADMIN")) {
											++adminCount;
											System.out.println(String.format("%-5s %-10s %-20s %-30s %-5s", adminCount,
													info.getUserId(), info.getUserName(), info.getUserEmailId(), info.getRole()));

										}
									}
									System.out.println("Total No. Of Admin Users : " + adminCount);

									System.out.println();
									System.out.println("Student Users");
									System.out.println(String.format("%-5s %-10s %-20s %-30s %-25s %-5s %-5s", "SNO.",
											"USER ID", "NAME", "EMAIL ID", "NO OF BOOKS BORROWED", "FINE", "ROLE"));

									for (UserPrimaryInfo info : users) {
										if (info.getRole().equalsIgnoreCase("STUDENT")) {
											++studentCount;
											System.out.println(String.format("%-5s %-10s %-20s %-30s %-25s %-5s %-5s",
													studentCount, info.getUserId(), info.getUserName(), info.getUserEmailId(),
													info.getNoOfBooksBorrowed(), info.getFine(), info.getRole()));
										}
									}
									System.out.println("Total No. of Student Users : " + studentCount);
									System.out.println();
									System.out.println("Total No. Of Users : " + users.size());
									System.out.println();
								} else {
									System.out.println("Users Not Found");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;
						case 10:
							System.out.println("Enter User Mail id: ");
							boolean flag = false;
							boolean isUpdated = false;
							int updateUserId = 0;
							do {
								updateUserId = InputData.checkId();
								if (updateUserId == id) {
									flag = true;
								} else {
									flag = false;
									System.err.println("Enter Your User Mail Id");
								}
							} while (!flag);
							System.out.println("Enter User password: ");
							String oldPassword = InputData.checkPassword();
							System.out.println("Enter New Password: ");
							String newPassword = InputData.checkPassword();

							try {

								isUpdated = userService.updatePassword(updateUserId, oldPassword, newPassword, "ADMIN");
								if (isUpdated) {
									System.out.println("Password Changed Successfully");
								} else {
									System.out.println("Password is Not Updated");
								}
							} catch (Exception e) {
								System.err.println(e.getMessage());
							}

						case 0:
							isLoggedIn = false;
							check = 0;
							break;

						default:
							System.err.println("Choice Should Be in Between 0 to 11");
							break;

						}
					} while (check != 0 || bookSearch || isLoggedIn);

				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.out.println("Please Re-Enter Login Credinetials");
				System.out.println("Please Re-Enter Login Credinetials");
				adminOperations();

			}
		} catch (LMSException e) {
			System.out.println("Please Re-Enter Login Credinetials");
			adminOperations();
		}
		return isLoggedIn;

	}

	// Search Page
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
			System.out.println("Press 4 to Search Exit");
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
					books = adminService.searchBook(bookInfo);
					if (books != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "AVAILABILITY"));

						for (BookPrimaryInfo book : books) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(),
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
					bookSearch = adminService.searchBook(bookInfo);
					if (bookSearch != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "AVAILABILITY"));

						for (BookPrimaryInfo book : bookSearch) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(),
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

					bookAuthor = adminService.searchBook(bookInfo);
					if (bookAuthor != null) {
						int i = 0;
						System.out.println(String.format("%-5s %-10s %-35s %-25s %-10s", "SNO.", "BOOK ID",
								"BOOK NAME", "AUTHOUR", "AVAILABILITY"));

						for (BookPrimaryInfo book : bookAuthor) {
							++i;
							System.out.println(String.format("%-5s %-10s %-35s %-25s  %-10s", i, book.getBookId(),
									book.getBookTitle(), book.getBookAuthourName(),
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
				System.out.println("");
				flag = true;
				searchBookBy = 0;
				break;
			case 0:
				LibraryController.loginForm();
				break;

			default:
				System.out.println("Invali Choice, Choice Must Be In Between 0 to 4");
				break;
			}

		} while (searchBookBy != 0);
		return flag;

	}
}


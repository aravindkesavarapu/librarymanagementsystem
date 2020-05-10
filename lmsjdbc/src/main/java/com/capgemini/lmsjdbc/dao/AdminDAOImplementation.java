package com.capgemini.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.RequestInfo;
import com.capgemini.lmsjdbc.dto.UserPrimaryInfo;
import com.capgemini.lmsjdbc.exception.LMSException;
import com.capgemini.lmsjdbc.utility.JdbcUtility;
import com.capgemini.lmsjdbc.utility.QueryMapper;

public class AdminDAOImplementation implements AdminDAO {

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement statement = connection.prepareStatement(QueryMapper.insertBook);) {
			statement.setInt(1, bookInfo.getBookId());
			statement.setString(2, bookInfo.getBookTitle());
			statement.setString(3, bookInfo.getBookAuthourName());
			statement.setBoolean(4, bookInfo.isAvailable());
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Cannot Add Book, As Book Id Already Exits");
			return false;
		}
		return true;
	}

	@Override
	public boolean issueBook(int requestId) {

		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement getReqStmt = connection.prepareStatement(QueryMapper.getRequest);
				PreparedStatement getUserStmt = connection.prepareStatement(QueryMapper.getUser);
				PreparedStatement issueStmt = connection.prepareStatement(QueryMapper.issueBook);
				PreparedStatement setBookAvailStmt = connection.prepareStatement(QueryMapper.setBookAvailability);
				PreparedStatement setBooksBorrowedStmt = connection
						.prepareStatement(QueryMapper.setNoOfBooksBorrowed);) {

			getReqStmt.setInt(1, requestId);
			try (ResultSet getReqResSet = getReqStmt.executeQuery();) {

				if (getReqResSet.next()) {
					int requestUserId = getReqResSet.getInt("userId");
					int requestBookId = getReqResSet.getInt("bookId");
					getUserStmt.setInt(1, requestUserId);

					try (ResultSet getUserResSet = getUserStmt.executeQuery();) {

						if (getUserResSet.next()) {
							UserPrimaryInfo users = new UserPrimaryInfo();
							users.setNoOfBooksBorrowed(getUserResSet.getInt("noOfBooksBorrowed"));
							int noOfBooksBorrowed = users.getNoOfBooksBorrowed();

							issueStmt.setInt(1, requestId);
							int updateDate = issueStmt.executeUpdate();
							if (updateDate != 0) {
								// Update book availability as false as we are issuing

								setBookAvailStmt.setInt(1, requestBookId);
								setBookAvailStmt.executeUpdate();

								// Update User no of books borrowed
								noOfBooksBorrowed++;

								setBooksBorrowedStmt.setInt(1, noOfBooksBorrowed);
								setBooksBorrowedStmt.setInt(2, requestUserId);
								setBooksBorrowedStmt.executeUpdate();

							} else {
								System.err.println("This Book is Already Issued");
								return false;
							}
						}
					} // End Of Gettinge User Result Set

				} else {
					System.err.println("InValid Request Id ");
					return false;
				}

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean isBookReceived(int requsetId) {
		int noOfDaysDelayed = 0;
		int fine = 0;
		int userId = 0;
		int boodId = 0;
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement getReqStmt = connection.prepareStatement(QueryMapper.getRequest);
				PreparedStatement fineStmt = connection.prepareStatement(QueryMapper.getfine);
				PreparedStatement setFineStmt = connection.prepareStatement(QueryMapper.userFine);
				PreparedStatement setBookAvailStmt = connection.prepareStatement(QueryMapper.setBookAvailability2);
				PreparedStatement setNoOfBooksStmt = connection.prepareStatement(QueryMapper.setNoOfBooksBorrowed2);
				PreparedStatement removeReqStmt = connection.prepareStatement(QueryMapper.removeRequest);) {

			getReqStmt.setInt(1, requsetId);
			try (ResultSet reqResSet = getReqStmt.executeQuery();) {
				while (reqResSet.next()) {
					Date returnedDate = reqResSet.getDate("returnedDate");
					Date expectedReturnedDate = reqResSet.getDate("expectedReturnDate");
					userId = reqResSet.getInt("userId");
					boodId = reqResSet.getInt("bookId");

					if (returnedDate != null) {
						fineStmt.setDate(1, returnedDate);
						fineStmt.setDate(2, expectedReturnedDate);
						fineStmt.setInt(3, requsetId);

						try (ResultSet fineResSet = fineStmt.executeQuery();) {
							while (fineResSet.next()) {
								noOfDaysDelayed = fineResSet.getInt(1);
							}
						}

						if (noOfDaysDelayed > 0) {
							fine = noOfDaysDelayed * 5;

							setFineStmt.setInt(1, fine);
							setFineStmt.setInt(2, userId);
							setFineStmt.executeUpdate();
						}

						// Make available in libaray books
						setBookAvailStmt.setInt(1, boodId);
						setBookAvailStmt.executeUpdate();

						// set No Of Books Borrowed
						setNoOfBooksStmt.setInt(1, userId);
						setNoOfBooksStmt.executeUpdate();

						removeReqStmt.setInt(1, requsetId);
						removeReqStmt.executeUpdate();

						return true;

					} else {
						System.err.println("Book Not Yet Returned, So You Can't Receive");
						return false;
					}
				} // End Of While Loop
			}
			System.err.println("Invalid Request Id");
			return false;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean removeBook(int bookId) {
		int noOfRowsAffected;
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement statement = connection.prepareStatement(QueryMapper.deleteBook)) {

			statement.setInt(1, bookId);
			noOfRowsAffected = statement.executeUpdate();
			if (noOfRowsAffected != 0) {
				return true;
			} else {
				System.err.println("Book Id Not Exists for Delete");
				return false;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public List<BookPrimaryInfo> getAllBooks() {
		try (Connection connection = JdbcUtility.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(QueryMapper.showBooks)) {
			List<BookPrimaryInfo> list = new LinkedList<BookPrimaryInfo>();

			while (resultSet.next()) {
				BookPrimaryInfo bookInfo = new BookPrimaryInfo();

				bookInfo.setBookId(resultSet.getInt("bookId"));
				bookInfo.setBookTitle(resultSet.getString("bookName"));
				bookInfo.setBookAuthourName(resultSet.getString("authourName"));
				bookInfo.setAvailable(resultSet.getBoolean("isAvailable"));

				list.add(bookInfo);
			}

			if (list.isEmpty()) {
				System.err.println("No Books Found In The Library");
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}

	}

	public List<UserPrimaryInfo> getAllUsers() {
		try (Connection connection = JdbcUtility.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(QueryMapper.showUsers)) {

			List<UserPrimaryInfo> list = new LinkedList<UserPrimaryInfo>();

			while (resultSet.next()) {
				UserPrimaryInfo users = new UserPrimaryInfo();

				users.setUserId(resultSet.getInt("id"));
				users.setUserName(resultSet.getString("name"));
				users.setUserEmailId(resultSet.getString("emailId"));
				users.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
				users.setRole(resultSet.getString("role"));
				users.setFine(resultSet.getDouble("fine"));

				list.add(users);
			}

			if (list.isEmpty()) {
				System.err.println("No User Found");
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo) {
		if (bookInfo.getBookId() != 0) {
			try (Connection connection = JdbcUtility.getConnection();
					PreparedStatement statement = connection.prepareStatement(QueryMapper.searchBookById)) {
				statement.setInt(1, bookInfo.getBookId());
				try (ResultSet resultSet = statement.executeQuery()) {
					List<BookPrimaryInfo> list = new LinkedList<BookPrimaryInfo>();
					if (resultSet.next()) {
						BookPrimaryInfo book = new BookPrimaryInfo();

						book.setBookId(resultSet.getInt("bookId"));
						book.setBookTitle(resultSet.getString("bookName"));
						book.setBookAuthourName(resultSet.getString("AuthourName"));
						book.setAvailable(resultSet.getBoolean("isAvailable"));

						list.add(book);
						return list;
					} else {

						System.err.println("No Book Found With The Given Id");
					}
				} // End Of Try Resource Result Set

			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Some Thing Went Wrong");
			}
		} else if (bookInfo.getBookTitle() != null) {
			try (Connection connection = JdbcUtility.getConnection();
					PreparedStatement statement = connection.prepareStatement(QueryMapper.searchBookByTitle)) {
				statement.setString(1, bookInfo.getBookTitle());
				try (ResultSet resultSet = statement.executeQuery()) {
					List<BookPrimaryInfo> list = new LinkedList<BookPrimaryInfo>();

					while (resultSet.next()) {
						BookPrimaryInfo book = new BookPrimaryInfo();

						book.setBookId(resultSet.getInt("bookId"));
						book.setBookTitle(resultSet.getString("bookName"));
						book.setBookAuthourName(resultSet.getString("authourName"));
						book.setAvailable(resultSet.getBoolean("isAvailable"));

						list.add(book);

					}
					if (list.isEmpty()) {
						System.err.println("No Books Found With Given Name");
					}
					return list;
				} // End Of Try Resource Result Set
			} // End Of Try Resource Connection, Prepared Statement
			catch (Exception e) {
				return null;
			}
		} else if (bookInfo.getBookAuthourName() != null) {
			try (Connection connection = JdbcUtility.getConnection();
					PreparedStatement statement = connection.prepareStatement(QueryMapper.searchBookByAuthour)) {
				statement.setString(1, bookInfo.getBookAuthourName());
				try (ResultSet resultSet = statement.executeQuery()) {
					List<BookPrimaryInfo> list = new LinkedList<BookPrimaryInfo>();

					while (resultSet.next()) {
						BookPrimaryInfo book = new BookPrimaryInfo();

						book.setBookId(resultSet.getInt("bookId"));
						book.setBookTitle(resultSet.getString("bookName"));
						book.setBookAuthourName(resultSet.getString("authourName"));
						book.setAvailable(resultSet.getBoolean("isAvailable"));
						list.add(book);
					}
					if (list.isEmpty()) {
						System.err.println("No Books Found With Given Authour");
					}
					return list;
				} // End Of Try Resource Result Set
			} // End Of Try Resource Connection, Prepared Statement
			catch (Exception e) {
				return null;
			}
		} else {
			System.err.println("Book Not Found ");
			return null;
		}
		return null;
	}

	public List<RequestInfo> getAllRequests() {
		try (Connection connection = JdbcUtility.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(QueryMapper.showRequests)) {

			List<RequestInfo> list = new LinkedList<RequestInfo>();

			while (resultSet.next()) {
				RequestInfo requestInfo = new RequestInfo();

				requestInfo.setBookId(resultSet.getInt("requestId"));
				requestInfo.setUserId(resultSet.getInt("userId"));
				requestInfo.setBookId(resultSet.getInt("bookId"));
				requestInfo.setIssuedDate(resultSet.getDate("issuedDate"));
				requestInfo.setExpectedReturnDate(resultSet.getDate("expectedReturnDate"));
				requestInfo.setReturnedDate(resultSet.getDate("returnedDate"));

				list.add(requestInfo);
			}
			if (list.isEmpty()) {
				System.err.println("No Requests Found");
				return null;
			} else {
				return list;
			}

		} catch (Exception e) {
			return null;
		}
	}

}

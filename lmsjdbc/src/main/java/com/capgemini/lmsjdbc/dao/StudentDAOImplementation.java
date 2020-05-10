package com.capgemini.lmsjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.lmsjdbc.dto.BookPrimaryInfo;
import com.capgemini.lmsjdbc.dto.RequestInfo;
import com.capgemini.lmsjdbc.utility.JdbcUtility;
import com.capgemini.lmsjdbc.utility.QueryMapper;

public class StudentDAOImplementation implements StudentDAO {

	public List<BookPrimaryInfo> getAllBooks() {
		try (Connection connection = JdbcUtility.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(QueryMapper.showBooks)) {
			List<BookPrimaryInfo> list = new LinkedList<BookPrimaryInfo>();

			while (resultSet.next()) {
				BookPrimaryInfo bookInfo = new BookPrimaryInfo();
				bookInfo.setBookId(resultSet.getInt("bookId"));
				bookInfo.setBookTitle(resultSet.getString("bookTitle"));
				bookInfo.setBookAuthourName(resultSet.getString("authourName"));
				bookInfo.setAvailable(resultSet.getBoolean("isAvailable"));
				list.add(bookInfo);
			}

			if (list.isEmpty()) {
				return null;
			} else {
				return list;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	public boolean requestBook(int userId, int bookId) {
		boolean isavail = false;
		int reqestedBookId = 0;
		int validBookId = 0;
		int noOfRequests = 0;
		try (Connection connection = JdbcUtility.getConnection();
				Statement isReqExists = connection.createStatement();
				PreparedStatement countReqStmt = connection.prepareStatement(QueryMapper.countRequests);
				PreparedStatement statement = connection.prepareStatement(QueryMapper.checkAvailability);
				PreparedStatement statement1 = connection.prepareStatement(QueryMapper.insertBookRequest);) {

			try (ResultSet resultSet = isReqExists.executeQuery(QueryMapper.showRequests)) {
				while (resultSet.next()) {
					reqestedBookId = resultSet.getInt("bookId");
					if (reqestedBookId == bookId) {
						System.err.println("Someone Has Already Placed This Book Request");
						return false;
					}
				}

				countReqStmt.setInt(1, userId);

				try (ResultSet countSet = countReqStmt.executeQuery()) {
					if (countSet.next()) {
						noOfRequests = countSet.getInt(1);
					}

					if (noOfRequests < 3) {
						statement.setInt(1, bookId);

						try (ResultSet isAvailSet = statement.executeQuery();) {
							while (isAvailSet.next()) {
								validBookId = isAvailSet.getInt("isbn");
								isavail = isAvailSet.getBoolean("isAvailable");
							}

							if (validBookId != 0) {
								if (isavail) {
									statement1.setInt(1, userId);
									statement1.setInt(2, bookId);
									statement1.executeUpdate();

									RequestInfo requestInfo = new RequestInfo();
									requestInfo.setUserId(userId);
									requestInfo.setBookId(bookId);

									return true;
								} else {
									System.err.println("Book Is Not Available For Borrowing");
									return false;
								}
							} else {
								System.err.println("Invalid Book Id");
								return false;
							}
						} // End Of ResultSet
					} else {
						System.err.println("Can't Place More Than 3 Requests");
						return false;
					}
				} // End Of Count ResultSet
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}

	}

	public boolean returnBook(int userId, int bookId) {
		try (Connection connection = JdbcUtility.getConnection();
				PreparedStatement getReqStmt = connection.prepareStatement(QueryMapper.getReqDetails);
				PreparedStatement updateDateStmt = connection.prepareStatement(QueryMapper.updateReturnDate);) {
			getReqStmt.setInt(1, userId);
			getReqStmt.setInt(2, bookId);

			try (ResultSet reqResSet = getReqStmt.executeQuery();) {
				if (reqResSet.next()) {
					int requestId = reqResSet.getInt("requestId");

					updateDateStmt.setInt(1, requestId);
					updateDateStmt.executeUpdate();

				} else {
					System.err.println("Invalid Return");
					return false;
				}

			}

		}

		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return true;

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
}
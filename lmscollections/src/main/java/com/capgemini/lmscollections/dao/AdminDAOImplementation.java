package com.capgemini.lmscollections.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public class AdminDAOImplementation implements AdminDAO {
	Date date = new Date();
	Date expectedReturnDate;
	Date returnedDate;

	@Override
	public boolean addBook(BookPrimaryInfo bookInfo) {
		for (BookPrimaryInfo bookBean : DataBase.BOOKDB) {
			if (bookBean.getBookId() == bookInfo.getBookId()) {
				return false;
			}
		}
		DataBase.BOOKDB.add(bookInfo);
		return true;

	}

	@Override
	public boolean removeBook(int bookId) {
		for (BookPrimaryInfo bookPrimaryInfo : DataBase.BOOKDB) {
			if (bookPrimaryInfo.getBookId() == bookId) {
				DataBase.BOOKDB.remove(bookPrimaryInfo);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		List<BookPrimaryInfo> booksList = new LinkedList<BookPrimaryInfo>();

		for (BookPrimaryInfo book : DataBase.BOOKDB) {
			book.getBookId();
			book.getBookTitle();
			book.getBookAuthourName();
			book.isAvailable();
			booksList.add(book);
		}

		if (booksList.isEmpty()) {
			return null;
		} else {
			return booksList;
		}

	}

	@Override
	public List<UserPrimaryInfo> getAllUsers() {
		List<UserPrimaryInfo> usersList = new LinkedList<UserPrimaryInfo>();

		for (UserPrimaryInfo userPrimaryInfo : DataBase.USERDB) {
			userPrimaryInfo.getUserId();
			userPrimaryInfo.getUserName();
			userPrimaryInfo.getUserEmailId();
			userPrimaryInfo.getNoOfBooksBorrowed();
			usersList.add(userPrimaryInfo);
		}

		if (usersList.isEmpty()) {
			return null;
		} else {
			return usersList;
		}

	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookPrimaryInfo) {
		List<BookPrimaryInfo> booksList = new LinkedList<BookPrimaryInfo>();
		for (BookPrimaryInfo bookBean : DataBase.BOOKDB) {

			if (bookBean.getBookId() == bookPrimaryInfo.getBookId()) {
				booksList.add(bookBean);
			} else if (bookBean.getBookTitle().equalsIgnoreCase(bookPrimaryInfo.getBookTitle())) {
				booksList.add(bookBean);

			} else if (bookBean.getBookAuthourName().equalsIgnoreCase(bookPrimaryInfo.getBookAuthourName())) {
				booksList.add(bookBean);
			} else if (bookBean.getBookCategory().equalsIgnoreCase(bookPrimaryInfo.getBookCategory())) {
				booksList.add(bookBean);
			}
		}

		if (booksList.isEmpty()) {
			return null;
		} else {
			return booksList;
		}

	}

	@Override
	public List<RequestInfo> getAllRequests() {

		List<RequestInfo> requestsList = new LinkedList<RequestInfo>();

		for (RequestInfo requestInfo : DataBase.REQUESTDB) {
			requestInfo.getBookId();
			requestInfo.getUserId();
			requestInfo.isIssued();
			requestInfo.isReturned();
			requestsList.add(requestInfo);
		}

		if (requestsList.isEmpty()) {
			return null;
		} else {
			return requestsList;
		}

	}

	@Override
	public boolean bookIssue(int userId, int bookId) {
		RequestInfo requestInfo = new RequestInfo();
		UserPrimaryInfo user = new UserPrimaryInfo();
		BookPrimaryInfo book = new BookPrimaryInfo();
		int noOfBooksBorrowed = 0;
		boolean isValidReq = false;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		for (RequestInfo requestInfo2 : DataBase.REQUESTDB) {
			if (requestInfo2.getUserId() == userId) {
				if ((requestInfo2.getBookId() == bookId) && (requestInfo2.isIssued() == false)) {
					isValidReq = true;
					requestInfo = requestInfo2;
				}
			}
		}

		if (isValidReq) {
			for (BookPrimaryInfo bookPrimaryInfo : DataBase.BOOKDB) {
				if (bookPrimaryInfo.getBookId() == bookId) {
					book = bookPrimaryInfo;
				}
			}

			for (UserPrimaryInfo userPrimaryInfo : DataBase.USERDB) {
				if (userPrimaryInfo.getUserId() == userId) {
					user = userPrimaryInfo;
					noOfBooksBorrowed = user.getNoOfBooksBorrowed();
				}
			}

			if (noOfBooksBorrowed < 3) {
				book.setAvailable(false);
				noOfBooksBorrowed++;
				user.setNoOfBooksBorrowed(noOfBooksBorrowed);
				requestInfo.setIssued(true);
				requestInfo.setIssuedDate(date);
				requestInfo.setExpectedReturnedDate(expectedReturnDate);
				return true;
			} else {
				DataBase.REQUESTDB.remove(requestInfo);
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public boolean isBookReceived(int userId, int bookId) {
		boolean isValidReceive = false;
		RequestInfo requestInfo = new RequestInfo();
		int noOfBooksBorrowed;
		double fine;

		for (RequestInfo requestInfo1 : DataBase.REQUESTDB) {
		if (requestInfo1.getBookId() == bookId && requestInfo1.getUserId() == userId
					&& requestInfo1.isReturned() == true) {
						isValidReceive = true;
				expectedReturnDate = requestInfo1.getExpectedReturnedDate();
				returnedDate = requestInfo1.getReturnedDate();
				requestInfo = requestInfo1;
				requestInfo.getBookId();
			}
		}

		if (isValidReceive) {
			long expReturnDateInMilliSecs = expectedReturnDate.getTime();
			long returnedDateInMilliSecs = returnedDate.getTime();
			long diffInMilliSecs = returnedDateInMilliSecs - expReturnDateInMilliSecs;
			int NoOfDaysDelayed = (int) (diffInMilliSecs / (24 * 60 * 60 * 1000));

			for (BookPrimaryInfo bookPrimaryInfo : DataBase.BOOKDB) {
			
				if (bookPrimaryInfo.getBookId() == bookId) {
					bookPrimaryInfo.setAvailable(true);
					break;
				}
			}

			for (UserPrimaryInfo userPrimaryInfo : DataBase.USERDB) {
				
				if (userPrimaryInfo.getUserId() == userId) {
						noOfBooksBorrowed = userPrimaryInfo.getNoOfBooksBorrowed();
					noOfBooksBorrowed--;
					userPrimaryInfo.setNoOfBooksBorrowed(noOfBooksBorrowed);
					fine = userPrimaryInfo.getFine();
					if (NoOfDaysDelayed > 0) {
						fine = fine + (NoOfDaysDelayed * 5);
						userPrimaryInfo.setFine(fine);
					}
					break;
				}
			}
			DataBase.REQUESTDB.remove(requestInfo);
			return true;
		}
		return false;
	}

	@Override
	public List<RequestInfo> getAllIssuedBooks() {
		// TODO Auto-generated method stub
		return null;
	}

}

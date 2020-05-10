package com.capgemini.lmscollections.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.lmscollections.db.DataBase;
import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.exception.LMSException;

public class StudentDAOImplementation implements StudentDAO {

	Date date = new Date();
	Date expectedReturnDate;
	Date returnedDate;

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		try {
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
		} catch (Exception e) {
			throw new LMSException("Books Not Found");
		}

	}

	@Override
	public boolean bookRequest(int userId, int bookId) {
		boolean isRequestExists = false;
		RequestInfo requestInfo = new RequestInfo();

		for (RequestInfo requestInfo1 : DataBase.REQUESTDB) {
			if (bookId == requestInfo1.getBookId()) {
				isRequestExists = true;
			}
		}

		if (!isRequestExists) {
			for (UserPrimaryInfo user : DataBase.USERDB) {
				if (userId == user.getUserId()) {
					for (BookPrimaryInfo book : DataBase.BOOKDB) {
						if ((book.getBookId() == bookId) && (book.isAvailable() == true)) {
							requestInfo.setUserId(userId);
							requestInfo.setBookId(bookId);
							requestInfo.setIssued(false);
							DataBase.REQUESTDB.add(requestInfo);
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, 20);
		returnedDate = calendar2.getTime();

		for (RequestInfo requestInfo : DataBase.REQUESTDB) {
			if (requestInfo.getBookId() == bookId && requestInfo.getUserId() == userId
					&& requestInfo.isIssued() == true) {
				requestInfo.setReturned(true);
				requestInfo.setReturnedDate(returnedDate);
				return true;
			}
		}
		return false;
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
				System.out.println("dsc");
				booksList.add(bookBean);
			}
		}

		if (booksList.isEmpty()) {
			return null;
		} else {
			return booksList;
		}

	}

}

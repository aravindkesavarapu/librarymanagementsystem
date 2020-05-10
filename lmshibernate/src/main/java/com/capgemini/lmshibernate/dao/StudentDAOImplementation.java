package com.capgemini.lmshibernate.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.RequestInfo;
import com.capgemini.lmshibernate.exception.LMSException;

public class StudentDAOImplementation implements StudentDAO {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistence");

	@Override
	public boolean requestBook(int userId, int bookId) {

		EntityManager manager = null;
		EntityTransaction transaction = null;
		boolean isRequested = false;
		try {

			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			RequestInfo info = new RequestInfo();
			BookPrimaryInfo bookInfo = new BookPrimaryInfo();

			String jpql = "select count(*) from RequestInfo ri where ri.userId=:uId";
			Query query = manager.createQuery(jpql);
			query.setParameter("uId", userId);
			int noOfReq = ((Number) query.getSingleResult()).intValue();
			System.out.println("no of req placed" + noOfReq);

			if (noOfReq < 3) {
				bookInfo = manager.find(BookPrimaryInfo.class, bookId);

				if (bookInfo != null) {
					jpql = "select ri from RequestInfo ri ";
					TypedQuery<RequestInfo> query2 = manager.createQuery(jpql, RequestInfo.class);
					List<RequestInfo> list = query2.getResultList();

					for (RequestInfo requestInfo : list) {
						if (requestInfo.getBookId() == bookId) {
							throw new LMSException("This Book Request is Already Placed By SomeOne ");
						}
					}

					if (bookInfo.isAvailable()) {
						transaction.begin();
						info.setUserId(userId);
						info.setBookId(bookId);
						manager.persist(info);
						isRequested = false;
						transaction.commit();
					} else {
						System.err.println("This Book Is Not Available For Borrowing");
						return isRequested;
					}

				} else {
					System.err.println("Invalid Book Id");
					return isRequested;
				}

			} else {
				System.err.println("You've Already Placed Maximum No Of Requests");
				return isRequested;
			}

		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return isRequested;
	}

	@Override
	public boolean returnBook(int userId, int bookId) {

		EntityManager manager = null;
		EntityTransaction transaction = null;
		boolean isReturned = false;
		RequestInfo info = new RequestInfo();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, 20);
		Date returnedDate = calendar2.getTime();
		int reqId = 0;
		try {

			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			String jpql = "select ri from RequestInfo ri ";
			TypedQuery<RequestInfo> query2 = manager.createQuery(jpql, RequestInfo.class);
			List<RequestInfo> list = query2.getResultList();

			for (RequestInfo requestInfo : list) {
				if ((requestInfo.getBookId() == bookId) && (requestInfo.getUserId() == userId)) {
					if (requestInfo.getReturnedDate() != null) {
						throw new LMSException("You Have Already Returned This Book");

					} else {
						reqId = requestInfo.getRequestId();
					}

				}
			}

			if (reqId != 0) {
				transaction.begin();
				info = manager.find(RequestInfo.class, reqId);
				info.setReturnedDate(returnedDate);
				isReturned = true;
				transaction.commit();

			} else {
				System.err.println("Invalid Book Return");
			}

		} catch (LMSException e) {
			System.err.println(e.getMessage());
			return isReturned;
		} finally {
			manager.close();
		}

		return isReturned;
	}

	@Override
	public List<BookPrimaryInfo> searchBook(BookPrimaryInfo bookInfo) {
		EntityManager manager = null;
		List<BookPrimaryInfo> list = null;
		BookPrimaryInfo info = new BookPrimaryInfo();
		list = new LinkedList<BookPrimaryInfo>();

		String jpql = null;
		int id = bookInfo.getBookId();
		String bookName = bookInfo.getBookTitle();
		String authourName = bookInfo.getBookAuthourName();
		String bookCategory = bookInfo.getBookCategory();
		try {
			manager = factory.createEntityManager();
			if (id != 0) {
				info = manager.find(BookPrimaryInfo.class, id);
//				if (info != null) {
				list.add(info);
//					return list;
//				} else {
//					throw new LMSException("Book Not Found With The Given Id");
//				}

			} else if (bookName != null) {

				jpql = "select books from BookPrimaryInfo books where books.bookTitle =:bookTitle ";
				TypedQuery<BookPrimaryInfo> query = manager.createQuery(jpql, BookPrimaryInfo.class);
				query.setParameter("bookTitle", bookName);
				list = query.getResultList();
//				if ((list != null) && (!list.isEmpty())) {
//					return list;
//				} else {
//					throw new LMSException("No Book Found With The Given Name");
//				}

			} else if (bookCategory != null) {

				jpql = "select books from BookPrimaryInfo books where books.bookCategory =:bookCategory";
				TypedQuery<BookPrimaryInfo> query = manager.createQuery(jpql, BookPrimaryInfo.class);
				query.setParameter("bookCategory", bookCategory);
				list = query.getResultList();
//				if ((list != null) && (!list.isEmpty())) {
//					return list;
//				} else {
//					throw new LMSException("No Book Found With The Given Name");
//				}
			}

			else if (authourName != null) {
				jpql = "select books from BookPrimaryInfo books where books.bookAuthourName = :aName";
				TypedQuery<BookPrimaryInfo> query = manager.createQuery(jpql, BookPrimaryInfo.class);
				query.setParameter("aName", authourName);
				list = query.getResultList();
//				if ((list != null) && (!list.isEmpty())) {
//					return list;
//				} else {
//					throw new LMSException("No Book Found With The Given Authour");
//				}
			} else {
//				throw new LMSException("InValid Searcing Of Books");
				return null;
			}
		} catch (LMSException e) {
//			e.printStackTrace();
//			System.out.println("Error caught in impl");
			System.err.println(e.getMessage());

		} finally {
			manager.close();
		}
		return list;
	}

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		List<BookPrimaryInfo> list = null;
		EntityManager manager = null;
		try {

			manager = factory.createEntityManager();
			String jpql = "select books from BookPrimaryInfo books";
			TypedQuery<BookPrimaryInfo> query = manager.createQuery(jpql, BookPrimaryInfo.class);
			list = query.getResultList();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			manager.close();
		}
		return list;
	}
}

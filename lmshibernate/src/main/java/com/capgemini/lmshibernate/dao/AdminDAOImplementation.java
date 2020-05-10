package com.capgemini.lmshibernate.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.capgemini.lmshibernate.dto.BookPrimaryInfo;
import com.capgemini.lmshibernate.dto.IssueInfo;
import com.capgemini.lmshibernate.dto.RequestInfo;
import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.exception.LMSException;

public class AdminDAOImplementation implements AdminDAO {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistence");

	@Override
	public boolean addBook(BookPrimaryInfo book)  {

		EntityManager manager = null;
		EntityTransaction transaction = null;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(book);
			transaction.commit();
			return true;
		}catch (Exception e) {
			transaction.rollback();
			return false;
		}
		finally {
			manager.close();
		}

	}

	@Override
	public boolean removeBook(int bookId) {

		EntityManager manager = null;
		EntityTransaction transaction = null;

		BookPrimaryInfo info = new BookPrimaryInfo();
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			info = manager.find(BookPrimaryInfo.class, bookId);
			manager.remove(info);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean issueBook(int requestId) {
		boolean isIssued = false;
		EntityManager manager = null;
		EntityTransaction transaction = null;

		int noOfBooks = 0;
		int reqBookId = 0;
		int reqUserId = 0;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			IssueInfo issueInfo = new IssueInfo();
			RequestInfo requestInfo = new RequestInfo();
			BookPrimaryInfo bookInfo = new BookPrimaryInfo();
			UserPrimaryInfo userInfo = new UserPrimaryInfo();

			transaction.begin();
			requestInfo = manager.find(RequestInfo.class, requestId);

			if (requestInfo != null) {
				Date issueDate = requestInfo.getIssuedDate();
				if (issueDate == null) {
					reqUserId = requestInfo.getUserId();
					reqBookId = requestInfo.getBookId();

//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					Calendar cal = Calendar.getInstance();
//					String issueDate1 = sdf.format(cal.getTime());
//					cal.add(Calendar.DAY_OF_MONTH, 7);
//					String expectedReturnDate = sdf.format(cal.getTime());

					Date date = new Date();
					Date expectedReturnDate = null;
					Calendar calendar = Calendar.getInstance();
					expectedReturnDate = calendar.getTime();

					requestInfo.setIssuedDate(date);
					requestInfo.setExpectedReturnDate(expectedReturnDate);
					transaction.commit();

					transaction.begin();
					userInfo = manager.find(UserPrimaryInfo.class, reqUserId);
					noOfBooks = userInfo.getNoOfBooksBorrowed();
					++noOfBooks;
					System.out.println("No Of Books Borrowed" + noOfBooks);

					userInfo.setNoOfBooksBorrowed(noOfBooks);
					manager.persist(userInfo);
					transaction.commit();

					transaction.begin();
					bookInfo = manager.find(BookPrimaryInfo.class, reqBookId);
					bookInfo.setAvailable(false);
					manager.persist(bookInfo);
					transaction.commit();

					transaction.begin();
					issueInfo.setBookId(requestInfo.getBookId());
					issueInfo.setBookName(bookInfo.getBookTitle());
					issueInfo.setBookAuthorName(bookInfo.getBookAuthourName());
					issueInfo.setReturned(false);
					issueInfo.setUserId(userInfo.getId());
					issueInfo.setUserName(userInfo.getName());
					manager.persist(issueInfo);
					transaction.commit();
				} else {
					throw new LMSException("This Book Is Already Issued");
				}

			} else {
				throw new LMSException("Invalid Request Id");
			}

		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return isIssued;
	}

	@Override
	public boolean isBookReceived(int requestId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		boolean isReceived = false;
		int noOfBooks = 0;
		int reqBookId = 0;
		int reqUserId = 0;
		double fine = 0;

		Date expectedReturnDate = null;
		Date returnedDate = null;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			RequestInfo requestInfo = new RequestInfo();
			BookPrimaryInfo bookInfo = new BookPrimaryInfo();
			UserPrimaryInfo userInfo = new UserPrimaryInfo();
			IssueInfo issueInfo = new IssueInfo();
			transaction.begin();
			requestInfo = manager.find(RequestInfo.class, requestId);

			if (requestInfo != null) {
				reqBookId = requestInfo.getBookId();
				reqUserId = requestInfo.getUserId();
				returnedDate = requestInfo.getReturnedDate();
				expectedReturnDate = requestInfo.getExpectedReturnDate();

				transaction.commit();

				if ((returnedDate != null) && (expectedReturnDate != null)) {
					long expReturnDateInMilliSecs = expectedReturnDate.getTime();
					long returnedDateInMilliSecs = returnedDate.getTime();
					long diffInMilliSecs = returnedDateInMilliSecs - expReturnDateInMilliSecs;
					int noOfDaysDelayed = (int) (diffInMilliSecs / (24 * 60 * 60 * 1000));

					transaction.begin();
					userInfo = manager.find(UserPrimaryInfo.class, reqUserId);
					noOfBooks = userInfo.getNoOfBooksBorrowed();
					--noOfBooks;
					userInfo.setNoOfBooksBorrowed(noOfBooks);
					if (noOfDaysDelayed > 0) {
						fine = userInfo.getFine();
						fine = fine + (noOfDaysDelayed * 5);
						userInfo.setFine(fine);
					}
					manager.persist(userInfo);
					transaction.commit();

					transaction.begin();
					bookInfo = manager.find(BookPrimaryInfo.class, reqBookId);
					bookInfo.setAvailable(true);
					manager.persist(bookInfo);
					transaction.commit();

					transaction.begin();
					requestInfo = manager.find(RequestInfo.class, requestId);
					manager.remove(requestInfo);
					transaction.commit();

					transaction.begin();
					issueInfo.setReturned(true);
					transaction.commit();
					isReceived = true;
				} else {
					throw new LMSException("User Not Yet Returned The Book");
				}

			} else {
				throw new LMSException("Invalid Request Id");
			}
		} catch (LMSException e) {
			System.err.println(e.getMessage());
			return isReceived;
		} finally {
			manager.close();
		}
		return isReceived;
	}

	@Override
	public List<BookPrimaryInfo> getAllBooks() {
		EntityManager manager = null;
		List<BookPrimaryInfo> list = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "select books from BookPrimaryInfo books";
			TypedQuery<BookPrimaryInfo> query = manager.createQuery(jpql, BookPrimaryInfo.class);
			list = query.getResultList();
//
//			if (list.isEmpty()) {
//				
//			} else {
//				return list;
//			}

		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return list;

	}

	@Override
	public List<UserPrimaryInfo> getAllUsers() {
		EntityManager manager = null;
		List<UserPrimaryInfo> list = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "select users from UserPrimaryInfo users";
			TypedQuery<UserPrimaryInfo> query = manager.createQuery(jpql, UserPrimaryInfo.class);
			list = query.getResultList();

			if (list.isEmpty()) {
System.err.println("No Users Found");
			return null;
			} else {
				return list;
			}

		} catch (Exception e) {
			return null;
		} finally {
			manager.close();
		}

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
				if ((list != null) && (!list.isEmpty())) {
					return list;
				} else {
					System.err.println("No Book Found With The Given Authour");
					return null;
				}
			} else {
				System.err.println("InValid Searcing Of Books");
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
	public List<RequestInfo> getAllRequests() {
		EntityManager manager = null;
		List<RequestInfo> list = null;
		try {
			manager = factory.createEntityManager();
			String jpql = "from RequestInfo requests";
			TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
			list = query.getResultList();
			if (list.isEmpty()) {
				System.err.println("No Requests Found");
				return null;
			} else {
				return list;
			}

		} catch (Exception e) {
			return null;
		} finally {
			manager.close();
		}
	}

}

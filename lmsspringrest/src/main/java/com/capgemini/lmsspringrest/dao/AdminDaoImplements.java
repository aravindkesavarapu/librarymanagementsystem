package com.capgemini.lmsspringrest.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.exception.LMSException;
@Repository
public class AdminDaoImplements implements AdminDao{

	@PersistenceUnit
	EntityManagerFactory factory = null;
	EntityManager manager = null;
	EntityTransaction transaction = null;
	int noOfBooks;
	@Override
	public boolean addBook(BookDetails bookDetail) {
		
		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
 			transaction.begin();
			manager.persist(bookDetail);
			transaction.commit();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		}finally {
			manager.close();
		}
	}

	@Override
	public boolean removeBook(int bookId) {
		
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails record = manager.find(BookDetails.class, bookId);
			manager.remove(record);
			transaction.commit();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean updateBook(BookDetails book) {
		

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails record = manager.find(BookDetails.class, book.getBookId());
			record.setBookName(book.getBookName());
			transaction.commit();
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
	}

	@Override
	public boolean bookIssue(int bookId, int id) {
		
		

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpql = "select b from bookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bId", bookId);
			BookDetails rs = query.getSingleResult();
			if (rs != null) {
				String jpql1 = "select r from RequestDetails r where r.uId=:uId and r.bookId=:bookId";
				TypedQuery<RequestDetails> query1 = manager.createQuery(jpql1, RequestDetails.class);
				//
				query1.setParameter("id", id);
				query1.setParameter("bookId", bookId);
				List<RequestDetails> rs1 = query1.getResultList();
				if (!rs1.isEmpty() && rs1 != null) {
					transaction.begin();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Calendar cal = Calendar.getInstance();
					String issueDate = sdf.format(cal.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 7);
					String returnDate = sdf.format(cal.getTime());
					BookIssue issueBook = new BookIssue();
					issueBook.setId(id);
					issueBook.setBookId(bookId);
					issueBook.setIssueDate(java.sql.Date.valueOf(issueDate));
					issueBook.setReturnDate(java.sql.Date.valueOf(returnDate));
					manager.persist(issueBook);
					transaction.commit();
					if (!rs1.isEmpty() && rs1 != null) {
						transaction.begin();
						Query bookName = manager
								.createQuery("select b.bookName from BookDetails b where b.bookId=:bookId");
						bookName.setParameter("bookId", bookId);
						List book = bookName.getResultList();
						BooksBorrowed borrowedBooks = new BooksBorrowed();
						//
						borrowedBooks.setId(id);
						borrowedBooks.setBookId(bookId);
						borrowedBooks.setBookName(book.get(0).toString());
						manager.persist(borrowedBooks);
						transaction.commit();
						return true;
					} else {
						throw new LMSException("Book Not issued");
					}
				} else {
					throw new LMSException("The respective user have not placed any request");
				}
			} else {
				throw new LMSException("There is no book exist with bookId" + bookId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}
		
	}

	@Override
	public List<RequestDetails> showRequests() {
		
		
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select r from RequestDetails r";
		TypedQuery<RequestDetails> query = manager.createQuery(jpql, RequestDetails.class);
		List<RequestDetails> recordList = query.getResultList();
		manager.close();
		factory.close();
		return recordList;
	}

	@Override
	public List<BookIssue> showIssuedBooks() {
		
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select b from BookIssue b";
		TypedQuery<BookIssue> query = manager.createQuery(jpql, BookIssue.class);
		List<BookIssue> recordList = query.getResultList();
		manager.close();
		return recordList;
	}

	@Override
	public List<User> showUsers() {
		
		
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select u from User u";
		TypedQuery<User> query = manager.createQuery(jpql, User.class);
		List<User> recordList = query.getResultList();
		
		manager.close();
		return recordList;
	}

}

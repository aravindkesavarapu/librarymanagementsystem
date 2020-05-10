package com.capgemini.lmsspringrest.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import com.capgemini.lmsspringrest.exception.LMSException;
@Repository
public class UserDaoImplements implements UserDao {
	@PersistenceUnit
	EntityManagerFactory factory ;
	EntityManager manager = null;
	EntityTransaction transaction = null;
	int noOfBooks;

	@Override
	public boolean request(int bookId, int id) {

		int count = 0;
		try {

	        factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpql = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bId", bookId);
			List rs = query.getResultList();
			if (rs != null) {
				String jpql1 = "select b from BooksBorrowed b where b.id=:id and b.bookId=:bookId";
				TypedQuery<BooksBorrowed> query1 = (TypedQuery<BooksBorrowed>) manager.createQuery(jpql1,
						BooksBorrowed.class);
				//
				query1.setParameter("Id", id);
				query1.setParameter("bId", bookId);
				List rs1 = query1.getResultList();
				if (rs1.isEmpty() || rs1 == null) {
					String jpql2 = "select b from BookIssue b where b.id=:id";
					TypedQuery<BookIssue> query2 = (TypedQuery<BookIssue>) manager.createQuery(jpql2, BookIssue.class);
					query2.setParameter("id", id);
					List<BookIssue> rs2 = query2.getResultList();
					for (BookIssue p : rs2) {
						noOfBooks = count++;
					}
					if (noOfBooks < 3) {
						Query bookName = manager
								.createQuery("select b.bookName from Bookdetails b where b.bookId=:bookId");
						bookName.setParameter("bookId", bookId);
						List book = bookName.getResultList();
						Query email = manager.createQuery("select u.email from User u where u.id=:id");
						email.setParameter("id", id);
						List userEmail = email.getResultList();
						transaction.begin();
						RequestDetails request = new RequestDetails();
						//
						request.setId(id);
						request.setBookId(bookId);
						request.setEmail(userEmail.get(0).toString());
						request.setBookName(book.get(0).toString());
						manager.persist(request);
						transaction.commit();
						return true;

					} else {
						throw new LMSException("You have crossed the book limit");
					}
				} else {
					throw new LMSException("You have already borrowed the requested book");
				}
			} else {
				throw new LMSException("The book with requested id is not present");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
			factory.close();
		}

	}



	@Override
	public boolean returnBook(int bookId, int id, String status) {

		try {

			 factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			String jpql = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bookId", bookId);
			BookDetails rs = query.getSingleResult();
			if (rs != null) {
				String jpql1 = "select b from BookIssue b where b.bookId=:bookId and b.id=:id ";
				TypedQuery<BookIssue> query1 = manager.createQuery(jpql1, BookIssue.class);
				query1.setParameter("bookId", bookId);
				//
				query1.setParameter("id", id);
				BookIssue rs1 = query1.getSingleResult();
				if (rs1 != null) {
					Date issueDate = rs1.getIssueDate();
					Calendar cal = Calendar.getInstance();
					Date returnDate = cal.getTime();
					long difference = issueDate.getTime() - returnDate.getTime();
					float daysBetween = (difference / (1000 * 60 * 60 * 24));
					if (daysBetween > 7.0) {
						// transaction.begin();
						float fine = daysBetween * 5;
						System.out.println("The user has to pay the fine of the respective book of Rs:" + fine);
						if (status == "yes") {
							transaction.begin();
							manager.remove(rs1);
							transaction.commit();
							transaction.begin();
							String jpql3 = "select b from booksBorrowed b  where b.bookId=:bookId and b.id=:id";
							Query query3 = manager.createQuery(jpql3);
							query3.setParameter("bookId", bookId);
							query3.setParameter("id", id);
							BooksBorrowed bbb = (BooksBorrowed) query3.getSingleResult();
							// int bbb_Id = bbb.getId();
							manager.remove(bbb);
							transaction.commit();

							transaction.begin();
							String jpql4 = "select r from RequestDetails r where r.bookId=:bookId and r.id=:id";
							Query query4 = manager.createQuery(jpql4);
							query4.setParameter("bookId", bookId);
							query4.setParameter("id", id);
							RequestDetails rdb = (RequestDetails) query4.getSingleResult();
							// int rdb_Id = rdb.getId();
							manager.remove(rdb);
							transaction.commit();
							return true;
						} else {
							throw new LMSException("The User has to pay fine for delaying book return");
						}
					} else {
						transaction.begin();

						manager.remove(rs1);
						transaction.commit();

						transaction.begin();
						String jpql3 = "select b from BooksBorrowed b  where b.bookId=:bId and b.id=:uid";
						Query query3 = manager.createQuery(jpql3);
						query3.setParameter("bookId", bookId);
						//
						query3.setParameter("id", id);
						BooksBorrowed bbb = (BooksBorrowed) query3.getSingleResult();
						manager.remove(bbb);
						transaction.commit();

						transaction.begin();
						String jpql4 = "select r from RequestDetails r where r.bookId=:bookId and r.uId=:uId";
						Query query4 = manager.createQuery(jpql4);
						query4.setParameter("bookId", bookId);
						//
						query4.setParameter("id", id);
						RequestDetails rdb = (RequestDetails) query4.getSingleResult();
						// int rdb_Id = rdb.getId();
						manager.remove(rdb);
						transaction.commit();
						return true;
					}

				} else {
					throw new LMSException("This respective user hasn't borrowed any book");
				}
			} else {
				throw new LMSException("book doesnt exist");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
			factory.close();
		}

	}

	@Override
	public LinkedList<Integer> bookHistoryDetails(int id) {

		int count = 0;
		 factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select b from BookIssue b";
		TypedQuery<BookIssue> query = manager.createQuery(jpql, BookIssue.class);
		List<BookIssue> recordList = query.getResultList();
		for (BookIssue p : recordList) {
			noOfBooks = count++;
		}
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(noOfBooks);
		manager.close();
		factory.close();
		//
		return list;

	}

	@Override
	public List<BooksBorrowed> borrowedBook(int id) {

		try {
			 factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			String jpql = "select b from BooksBorrowed b where b.uId=:uId";
			TypedQuery<BooksBorrowed> query = manager.createQuery(jpql, BooksBorrowed.class);
			//
			query.setParameter("uId", id);
			List<BooksBorrowed> recordList = query.getResultList();
			return recordList;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			manager.close();
			factory.close();
		}
	}


}

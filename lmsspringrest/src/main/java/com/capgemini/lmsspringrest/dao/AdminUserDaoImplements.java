package com.capgemini.lmsspringrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.exception.LMSException;
@Repository
public class AdminUserDaoImplements implements AdminUserDao{
 
	@PersistenceUnit
	EntityManagerFactory factory = null;
	EntityManager manager = null;
	EntityTransaction transaction = null;
	int noOfBooks;
	@Override
	public boolean registerUser(User user) {
		
		boolean isRegistered = false;
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(user);
			transaction.commit();
			isRegistered = true;
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			// return false;
		}
		return isRegistered;
	}

	@Override
	public User authUser(String email, String password) {
		
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			
			String jpql = "select u from User u where u.email=:email and u.password=:password";
			transaction.begin();
			TypedQuery<User> query = manager.createQuery(jpql, User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
		//	List list = query.getResultList();
			User bean = query.getSingleResult();
			transaction.commit();
			return bean;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookByTitle(String bookName) {
		
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.bookName=:bookName";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bookName", bookName);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookByAuthor(String authorName) {
		
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.authorName=:authorName";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("authorName", authorName);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> searchBookById(int bookId) {
		
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select b from BookDetails b where b.bookId=:bookId";
			TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
			query.setParameter("bookId", bookId);
			List<BookDetails> recordList = query.getResultList();
			transaction.commit();
			return recordList;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		} finally {
			manager.close();
		}
	}

	@Override
	public List<BookDetails> getBooksInfo() {
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();	
		transaction = manager.getTransaction();
		transaction.begin();
		String jpql = "select b from BookDetails b";
		TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
		List<BookDetails> recordList = query.getResultList();
		transaction.commit();
		manager.close();
		return recordList;
	}

	@Override
	public boolean updatePassword(int id, String password, String newPassword, String role) {
	

		try {
			
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			String jpql = "select u from User u where u.id=:id and u.role=:role and u.password=:password";
			TypedQuery<User> query = manager.createQuery(jpql, User.class);
			query.setParameter("id", id);
			query.setParameter("role", role);
			query.setParameter("password", password);
			User rs = query.getSingleResult();
			if (rs != null) {
				User record = manager.find(User.class, id);
				record.setPassword(newPassword);
				transaction.commit();
				return true;
			} else {
				throw new LMSException("User doesnt exist");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			transaction.rollback();
			return false;
		} finally {
			manager.close();
		}	
	}

}

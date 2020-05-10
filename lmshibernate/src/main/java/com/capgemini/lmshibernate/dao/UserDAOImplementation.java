package com.capgemini.lmshibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.capgemini.lmshibernate.dto.UserPrimaryInfo;
import com.capgemini.lmshibernate.exception.LMSException;

public class UserDAOImplementation implements UserDAO {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("TestPersistence");

	@Override
	public boolean register(UserPrimaryInfo user, String userRole) {
		boolean isRegistered = false;
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		boolean flag = false;
		try {

			String jpqlQuery = "from UserPrimaryInfo user ";
			TypedQuery<UserPrimaryInfo> query = manager.createQuery(jpqlQuery, UserPrimaryInfo.class);
			List<UserPrimaryInfo> list = query.getResultList();

			for (UserPrimaryInfo userInfo1 : list) {
				if (userInfo1.getEmailId().equalsIgnoreCase(user.getEmailId())) {
					flag = false;
				}
			}

			transaction.begin();
			flag = true;
			manager.persist(user);
			isRegistered = true;
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (flag) {
				transaction.rollback();
			}
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return isRegistered;
	}

	@Override
	public boolean userAuthentication(int id, String password, String userRole) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		boolean isLoggedIn = false;
		try {
			transaction.begin();
			userInfo = manager.find(UserPrimaryInfo.class, id);
			if (userInfo != null) {
				if (userInfo.getId() == id) {
					if (userInfo.getId() == id && userInfo.getPassword().equals(password)
							&& userInfo.getRole().equalsIgnoreCase(userRole)) {
						transaction.commit();
						isLoggedIn = true;
					} else {
						throw new LMSException("Invalid password");
					}
				} else {
					throw new LMSException("Mail Id Not Exists");
				}
			} else {
				throw new LMSException("Invalid " + userRole + " User Id");
			}
		} catch (Exception e) {
			transaction.rollback();
			System.err.println(e.getMessage());
			return isLoggedIn;
		} finally {
			manager.close();
		}
		return isLoggedIn;
	}

	@Override
	public boolean updatePassword(int id, String oldPassword, String newPassword, String userRole) {
		EntityManager manager = factory.createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		UserPrimaryInfo userInfo = new UserPrimaryInfo();
		boolean isUpdated = false;
		try {
			transaction.begin();
			userInfo = manager.find(UserPrimaryInfo.class, id);
			if (userInfo.getId() == id && userInfo.getPassword().equals(oldPassword)
					&& userInfo.getRole().equalsIgnoreCase(userRole)) {
				userInfo.setPassword(newPassword);
				isUpdated = true;
				transaction.commit();
			} else {
				
				throw new LMSException("Invalid Password");
			}
		} catch (LMSException e) {
			System.err.println(e.getMessage());
		}

		finally {
			manager.close();
		}
		return isUpdated;
	}

}

package com.capgemini.lmscollections.factory;

import com.capgemini.lmscollections.dao.AdminDAO;
import com.capgemini.lmscollections.dao.AdminDAOImplementation;
import com.capgemini.lmscollections.dao.StudentDAO;
import com.capgemini.lmscollections.dao.StudentDAOImplementation;
import com.capgemini.lmscollections.dao.UserDAO;
import com.capgemini.lmscollections.dao.UserDAOImplementation;
import com.capgemini.lmscollections.service.AdminService;
import com.capgemini.lmscollections.service.AdminServiceImplementation;
import com.capgemini.lmscollections.service.StudentService;
import com.capgemini.lmscollections.service.StudentServiceImplementation;
import com.capgemini.lmscollections.service.UserService;
import com.capgemini.lmscollections.service.UserServiceImplementation;

public class LibraryFactory {

	public static UserDAO getUserDAO() {
		return new UserDAOImplementation();
	}

	public static AdminDAO getAdminDAO() {
		return new AdminDAOImplementation();
	}

	public static StudentDAO getStudentDAO() {
		return new StudentDAOImplementation();
	}

	public static UserService getUserService() {
		return new UserServiceImplementation();
	}

	public static AdminService getAdminService() {
		return new AdminServiceImplementation();
	}

	public static StudentService getStudentService() {
		return new StudentServiceImplementation();
	}
}

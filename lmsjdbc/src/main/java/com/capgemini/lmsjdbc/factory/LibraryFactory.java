package com.capgemini.lmsjdbc.factory;

import com.capgemini.lmsjdbc.dao.AdminDAO;
import com.capgemini.lmsjdbc.dao.AdminDAOImplementation;
import com.capgemini.lmsjdbc.dao.StudentDAO;
import com.capgemini.lmsjdbc.dao.StudentDAOImplementation;
import com.capgemini.lmsjdbc.dao.UserDAO;
import com.capgemini.lmsjdbc.dao.UserDAOImplementation;
import com.capgemini.lmsjdbc.service.AdminService;
import com.capgemini.lmsjdbc.service.AdminServiceImplementation;
import com.capgemini.lmsjdbc.service.StudentService;
import com.capgemini.lmsjdbc.service.StudentServiceImplementation;
import com.capgemini.lmsjdbc.service.UserService;
import com.capgemini.lmsjdbc.service.UserServiceImplementation;

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

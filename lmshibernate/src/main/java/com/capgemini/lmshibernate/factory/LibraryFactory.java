package com.capgemini.lmshibernate.factory;

import com.capgemini.lmshibernate.dao.AdminDAO;
import com.capgemini.lmshibernate.dao.AdminDAOImplementation;
import com.capgemini.lmshibernate.dao.StudentDAO;
import com.capgemini.lmshibernate.dao.StudentDAOImplementation;
import com.capgemini.lmshibernate.dao.UserDAO;
import com.capgemini.lmshibernate.dao.UserDAOImplementation;
import com.capgemini.lmshibernate.service.AdminService;
import com.capgemini.lmshibernate.service.AdminServiceImplementation;
import com.capgemini.lmshibernate.service.StudentService;
import com.capgemini.lmshibernate.service.StudentServiceImplementation;
import com.capgemini.lmshibernate.service.UserService;
import com.capgemini.lmshibernate.service.UserServiceImplementation;


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

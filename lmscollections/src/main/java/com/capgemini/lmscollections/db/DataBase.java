package com.capgemini.lmscollections.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.capgemini.lmscollections.dto.BookPrimaryInfo;
import com.capgemini.lmscollections.dto.RequestInfo;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;

public class DataBase {

	public static final List<UserPrimaryInfo> USERDB = new ArrayList<UserPrimaryInfo>();
	public static final List<BookPrimaryInfo> BOOKDB = new ArrayList<BookPrimaryInfo>();
	public static final List<RequestInfo> REQUESTDB = new ArrayList<RequestInfo>();

	public static void addToDB() {
		
		//ADMIN 
		USERDB.add(new UserPrimaryInfo(101, "aravind", "aravind@gmail.com", "aravind", 0, "ADMIN", 0));
		USERDB.add(new UserPrimaryInfo(102, "google", "google@gmail.com", "google", 0, "ADMIN", 0));
		
		//STUDENT
		USERDB.add(new UserPrimaryInfo(101, "karavind", "karavind@gmail.com", "karavind", 0, "STUDENT", 0));
		USERDB.add(new UserPrimaryInfo(102, "gmail", "gmail@gmail.com", "gmail", 0, "STUDENT", 0));
		
	//	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		//Issued
		REQUESTDB.add(new RequestInfo(800, 101, true, false, date, date, date));
		REQUESTDB.add(new RequestInfo(801, 102, true, false, date, date, date));
//		public RequestInfo(int bookId, int userId,boolean isIssued,boolean isReturned,Date issuedDate,Date expectedReturnedDate,Date returnedDate) {

		//
		REQUESTDB.add(new RequestInfo(802, 101, false, true, date, date, date));
		REQUESTDB.add(new RequestInfo(803, 102, false, true, date, date, date));

		BOOKDB.add(new BookPrimaryInfo(800, "Java", "James","ECE", false));
		BOOKDB.add(new BookPrimaryInfo(801, "Monk Who Sold His Ferari", "Robin Sharma","EEE", true));
		BOOKDB.add(new BookPrimaryInfo(802, "Life", "Gopal Das","EEE", true));
		BOOKDB.add(new BookPrimaryInfo(804, "Life", "Gopal Das","EEE", true));
			
	}
}

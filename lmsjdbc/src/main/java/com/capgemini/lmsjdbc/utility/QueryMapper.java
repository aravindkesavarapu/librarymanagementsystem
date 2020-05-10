package com.capgemini.lmsjdbc.utility;

public interface QueryMapper {
	
	String userExists = "select * from UserPrimaryInfo where id = ? or emailId = ?";
	
	String insertQuery = "insert into UserPrimaryInfo (id,name,emailId,password,noOfBooksBorrowed,role) values(?,?,?,?,?,?)";

	String adminLogin = "select * from UserPrimaryInfo where id = ? and password = ? and role = \"admin\"";

	String userLogin = "select * from UserPrimaryInfo where id = ? and password = ?";

	String insertBook = "insert into BookPrimaryInfo (bookId,bookName,authourName,isAvailable) values(?,?,?,?)";

	String deleteBook = "delete from BookPrimaryInfo where bookId = ?";

	String showBooks = "select * from BookPrimaryInfo";

	String showUsers = "select * from UserPrimaryInfo";

	String showRequests = "select * from RequestInfo";

	String countRequests = "select count(*) from RequestInfo where id = ?";

	String checkAvailability = "select * from BookPrimaryInfo where bookId = ?";

	String insertBookRequest = "insert into RequestInfo (id,bookId) values(?,?)";

	String getRequest = "select * from RequestInfo where requestId = ?";

	String getUser = "select * from UserPrimaryInfo where id = ?";

	String issueBook = "update RequestInfo set issuedDate = now(), expectedReturnDate = date_add(issuedDate, interval 15 day) where requestId = ? and issuedDate is null";

	String setBookAvailability = "update BookPrimaryInfo set isAvailable = false where bookId = ?";

	String setNoOfBooksBorrowed = "update UserPrimaryInfo set noOfBooksBorrowed = ? where id = ?";

	String deleteRequest = "delete from RequestInfo where requestId = ?";

	String getReqDetails = "select * from RequestInfo where (id = ? and bookId = ?)  and (issuedDate  is not null and returnedDate is null)";

	String updateReturnDate = "update RequestInfo set returneddate = '2020-05-30' where requestId = ?";

	String getfine = "select datediff(?,?) from RequestInfo where requestId = ?";

	String userFine = "update UserPrimaryInfo set fine = fine + ? where id = ?";

	String setBookAvailability2 = "update BookPrimaryInfo set isAvailable = true where bookId = ?";

	String setNoOfBooksBorrowed2 = "update UserPrimaryInfo set noOfBooksBorrowed =  noOfBooksBorrowed -1 where id = ?";

	String removeRequest = "delete from  RequestInfo  where requestId = ?";

	String searchBookById = "select * from BookPrimaryInfo where bookId = ?";

	String searchBookByTitle = "select * from BookPrimaryInfo where bookName = ?";

	String searchBookByAuthour = "select * from BookPrimaryInfo where authourName = ?";

	String setPassword = "update UserPrimaryInfo set password = ? where password = ? and id = ?";

}

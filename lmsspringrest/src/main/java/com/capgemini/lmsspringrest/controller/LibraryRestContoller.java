package com.capgemini.lmsspringrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.lmsspringrest.dto.BookDetails;
import com.capgemini.lmsspringrest.dto.BookIssue;
import com.capgemini.lmsspringrest.dto.BooksBorrowed;
import com.capgemini.lmsspringrest.dto.LibraryResponse;
import com.capgemini.lmsspringrest.dto.RequestDetails;
import com.capgemini.lmsspringrest.dto.User;
import com.capgemini.lmsspringrest.service.AdminService;
import com.capgemini.lmsspringrest.service.AdminUserService;
import com.capgemini.lmsspringrest.service.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
public class LibraryRestContoller {

	@Autowired
	private UserService userService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminUserService service ;

	@PostMapping(path = "/addUser", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })

	public LibraryResponse addUser(@RequestBody User bean) {
		boolean isAdded = service.registerUser(bean);
		LibraryResponse response = new LibraryResponse();
		if (isAdded) {
			response.setMessage("record inserted");
		} else {
			response.setError(true);
			response.setMessage("unable to add");
		}
		return response;
	}

	@PostMapping(path = "/addBook", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse addBook(@RequestBody BookDetails bean) {
		boolean isBookAdded = adminService.addBook(bean);
		LibraryResponse response = new LibraryResponse();
		if (isBookAdded) {
			response.setMessage("Book added succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book cannot be added");
		}
		return response;

	}

	@PutMapping(path = "/updateBook", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse updateBook(@RequestBody BookDetails bean) {
		boolean isBookUpdated = adminService.updateBook(bean);
		LibraryResponse response = new LibraryResponse();
		if (isBookUpdated) {
			response.setMessage("Book updated succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book cannot be updated");
		}
		return response;

	}

//	@PostMapping(path = "/login/{email}/{password}", consumes = { MediaType.APPLICATION_JSON_VALUE,
//			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
//					MediaType.APPLICATION_XML_VALUE })
//	public LibraryResponse authentication(
//			@PathVariable(name="email")String email,
//			@PathVariable(name="password" ) String password){
//		User userLogin = service.authUser(email, password);
//		LibraryResponse response = new LibraryResponse();
//		if (userLogin != null) {
//			response.setMessage("Login succesfully");
//		} else {
//			response.setError(true);
//			response.setMessage("Invalid credentials,Please try again");
//		}
//	 	return response;
// 	}

	@DeleteMapping(path = "/removeBook/{bookId} ", 
			produces = { MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse deleteBook(@PathVariable(name = "bookId") int bookId) {
		boolean isBookDeleted = adminService.removeBook(bookId);
		LibraryResponse response = new LibraryResponse();
		if (isBookDeleted) {
			response.setMessage("Book deleted succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book not deleted");
		}
		return response;
	}

	@GetMapping(path = "/booksInfo", 
			produces = { MediaType.APPLICATION_JSON_VALUE, 
					MediaType.APPLICATION_XML_VALUE }
	)
	public LibraryResponse getBookInfo() {
		List<BookDetails> getInfo = service.getBooksInfo();
		LibraryResponse response = new LibraryResponse();
		if (getInfo != null && !getInfo.isEmpty()) {
			response.setMessage("Books found");
			response.setBook(getInfo);
		} else {
			response.setError(true);
			response.setMessage("They are no books in the Library");
		}
		return response;
	}

	@GetMapping(path = "/booksByName/{bookName}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse getBookByName(@PathVariable(name = "bookName") String bookName) {
		List<BookDetails> bean = service.searchBookByTitle(bookName);
		LibraryResponse response = new LibraryResponse();
		if (bean != null && !bean.isEmpty()) {
			response.setMessage("Books found");
			response.setBook(bean);
		} else {
			response.setError(true);
			response.setMessage("They are no books in the Library");
		}
		return response;
	}

	@GetMapping(path = "/booksByAuthor/{authorName}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse getBookByAuthor(@PathVariable(name = "authorName") String authorName) {
		List<BookDetails> bean = service.searchBookByAuthor(authorName);
		LibraryResponse response = new LibraryResponse();
		if (bean != null && !bean.isEmpty()) {
			response.setMessage("Books found");
			response.setBook(bean);
		} else {
			response.setError(true);
			response.setMessage("They are no books in the Library");
		}
		return response;
	}

	@GetMapping(path = "/booksById/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse getBookById(@PathVariable(name = "bookId")int bookId) {
		List<BookDetails> bean = service.searchBookById(bookId);
		LibraryResponse response = new LibraryResponse();
		if (bean != null && !bean.isEmpty()) {
			response.setMessage("Books found");
			response.setBook(bean);
		} else {
			response.setError(true);
			response.setMessage("They are no books in the Library");
		}
		return response;
	}

	@PostMapping(path = "/bookIssue", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse issueBook(
			@RequestParam(name = "userId")int userId,
			@RequestParam(name = "bookId") int bookId) {
		boolean isBookIssued = adminService.bookIssue(userId, bookId);
		LibraryResponse response = new LibraryResponse();
	 	if (isBookIssued) {
			response.setMessage("Book issued succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book cannot be issued");
		}
		return response;
	}

	@PostMapping(path = "/returnBook", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse returnBook(
			@RequestParam(name = "bookId")int bookId,
			@RequestParam(name="userId")int userId,
			@RequestParam(name = "status") String status) {
		boolean isBookReturned = userService.returnBook(bookId, userId, status);
		LibraryResponse response = new LibraryResponse();
		if (isBookReturned) {
			response.setMessage("Book returned succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book cannot be returned");
		}
		return response;
	}

	@PostMapping(path = "/requestBook", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse requestBook(
			@RequestParam(name = "bookId") int bookId,
			@RequestParam(name = "id") int id) {
		boolean isBookRequested = userService.request(bookId, id);
		LibraryResponse response = new LibraryResponse();
		if (isBookRequested) {
			response.setMessage("Book requested succesfully");
		} else {
			response.setError(true);
			response.setMessage("Book cannot be requested");
		}
		return response;
	}

	@GetMapping(path = "/showRequests", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse showRequests() {
		List<RequestDetails> detailList = adminService.showRequests();
		LibraryResponse response = new LibraryResponse();

		if (detailList != null && !detailList.isEmpty()) {
			response.setDetails(detailList);
		} else {
			response.setError(true);
			response.setMessage("They are no requests");
		}
		return response;
	}

	@GetMapping(path = "/showIssuedBooks", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse showIssuedBooks() {
		List<BookIssue> issueList = adminService.showIssuedBooks();
		LibraryResponse response = new LibraryResponse();

		if (issueList != null && !issueList.isEmpty()) {
			response.setIssue(issueList);
		} else {
			response.setError(true);
			response.setMessage("No Books are Issued");
		}
		return response;
	}

//	@GetMapping(path = "/showUsers", produces = { MediaType.APPLICATION_JSON_VALUE, 
//			MediaType.APPLICATION_XML_VALUE })
//	public LibraryResponse showUsers() {
//		List<User> usersList = adminService.showUsers();
//		LibraryResponse response = new LibraryResponse();
//
//		if (usersList != null && !usersList.isEmpty()) {
//			response.setUsers(usersList);
//		} else {
//			response.setError(true);
//			response.setMessage("They are no Users");
//		}
//		return response;
//	}

//	@PutMapping(path = "/updatePassword", produces = { MediaType.APPLICATION_JSON_VALUE,
//			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
//					MediaType.APPLICATION_XML_VALUE })
//	public LibraryResponse updatePassord(int id, String password, String newPassword, String role) {
//		boolean isUpdated = service.updatePassword(id, password, newPassword, role);
//		LibraryResponse response = new LibraryResponse();
//
//		if (isUpdated) {
//			response.setMessage("Password updated successfully");
//		} else {
//			response.setError(true);
//			response.setMessage("Password is not updated");
//		}
//		return response;
//	}

	@GetMapping(path = "/borrowedBooks/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse getBorrowedBooks(@PathVariable(name = "bookId")int id) {
		List<BooksBorrowed> borrowed = userService.borrowedBook(id);
		LibraryResponse response = new LibraryResponse();

		if (borrowed != null && !borrowed.isEmpty()) {
			response.setBorrowed(borrowed);
		} else {
			response.setError(true);
			response.setMessage("There are no borrowed  books");
		}
		return response;
	}

}

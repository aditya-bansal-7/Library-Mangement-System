package com.adityabansal7.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adityabansal7.exception.BookException;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.service.LibraryLogService;
import com.adityabansal7.service.LibraryLogServiceImpl;

@RestController
@RequestMapping("/libraryLog")
public class LibraryLogController {

	@Autowired
	LibraryLogService libraryLogService;
	
	//To issue a book from the library.
	@GetMapping("/issueBook/userId/{userId}/bookId/{bookId}")
	public ResponseEntity<String> addEntryIntoLog(@PathVariable Integer userId, @PathVariable Integer bookId) throws UserException, BookException{
		
		String dueDate = libraryLogService.issueBook(userId, bookId);
		
		return new ResponseEntity<String>(dueDate, HttpStatus.CREATED);
	}
	
	//To return the issued the book.
	@GetMapping("/returnBook/bookId/{bookId}/userId/{userId}")
    public ResponseEntity<String> returnBook(@PathVariable("userId") Integer userId, @PathVariable("bookId") Integer bookId,@RequestParam String returnDate) throws UserException, BookException{
		LocalDate date =  LocalDate.parse(returnDate);
		String message = libraryLogService.returnBook(userId, bookId, date);
		
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}
	
}

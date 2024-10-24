package com.adityabansal7.service;

import java.time.LocalDate;

import com.adityabansal7.exception.BookException;
import com.adityabansal7.exception.UserException;

public interface LibraryLogService {

	public String issueBook(Integer userId, Integer bookId) throws UserException, BookException;
	public String returnBook(Integer userId, Integer bookId, LocalDate returnDate) throws UserException, BookException;
	
}

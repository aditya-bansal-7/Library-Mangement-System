package com.adityabansal7.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adityabansal7.exception.BookException;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.Book;
import com.adityabansal7.model.LibraryLog;
import com.adityabansal7.model.User;
import com.adityabansal7.repository.BookRepository;
import com.adityabansal7.repository.LibraryLogRepository;
import com.adityabansal7.repository.UserRepository;

@Service
public class LibraryLogServiceImpl implements LibraryLogService{

	@Autowired
	LibraryLogRepository libraryLogRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BookRepository bookRepo;
	
	//Issuing a book and maintaining log. 
	@Override
	public String issueBook(Integer userId, Integer bookId) throws UserException, BookException {
		
		User existingUser = userRepo.findById(userId).orElseThrow(() -> new UserException("Invalid userId"));
		Book existingBook = bookRepo.findById(bookId).orElseThrow(() -> new BookException("Invalid bookId"));
		
		boolean isBoolAssigned = assignBookToUser(existingUser,existingBook);
		if(isBoolAssigned) {
			LibraryLog libraryLog =  LibraryLog.builder()
					                           .userId(userId)
					                           .bookId(bookId)
					                           .bookIssueDate(LocalDate.now())
					                           .bookDueDate(LocalDate.now().plusDays(8))
					                           .bookReturnDate(null)
					                           .Fine(0)
					                           .build();
			libraryLogRepo.save(libraryLog);
		}else {
			if(existingUser.getBookList().size() == 5) throw new UserException("You have reached the max limit");
			else throw new BookException("This book is not available");
		}
		return "Last date to submit the book is "+ LocalDate.now().plusDays(8);
	}
	
	//Assigning the book to user if the book available and user shouldn't issued more than 5 book.
	public boolean assignBookToUser(User user,Book book) {
		int maxBookPerPerson = 5;
		boolean isBookAvailable = book.getAvailability();
		
		if(user.getBookList().size() < maxBookPerPerson && isBookAvailable) {
			user.getBookList().add(book);
			book.setAvailability(false);
			userRepo.save(user);
			return true;
		}
		else {
			return false;
		}
	}

	//Returning the book and calculating the find if any.
	@Override
	public String returnBook(Integer userId, Integer bookId, LocalDate returnDate) throws UserException, BookException {
		int totalFine = 0;
		int finePerDay = 10;
		
		LibraryLog libraryLog = libraryLogRepo.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new UserException("No match found"));
		int difference = returnDate.compareTo(libraryLog.getBookDueDate());
		
		User existingUser = userRepo.findById(userId).get();
		Book existingBook = bookRepo.findById(bookId).get();
		
		if(difference > 0) {
			int dateDiff = Math.abs(Period.between(returnDate, libraryLog.getBookDueDate()).getDays());
			totalFine = dateDiff * finePerDay;
			existingUser.getBookList().remove(existingBook);
			existingBook.setAvailability(true);
			userRepo.save(existingUser);
			libraryLog.setBookReturnDate(returnDate);
			libraryLog.setFine(totalFine);
			libraryLogRepo.save(libraryLog);
			return "Return date is higher than due date so the fine is "+ totalFine;
		}
		else{
			existingUser.getBookList().remove(existingBook);
			existingBook.setAvailability(true);
			userRepo.save(existingUser);
			libraryLog.setBookReturnDate(returnDate);
			libraryLogRepo.save(libraryLog);
			return "Book is returned before due date";
		}
	}
	
	

}
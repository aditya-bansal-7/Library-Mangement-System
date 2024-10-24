package com.adityabansal7.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adityabansal7.exception.BookException;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.Book;
import com.adityabansal7.model.LibraryLog;
import com.adityabansal7.model.User;
import com.adityabansal7.repository.BookRepository;
import com.adityabansal7.repository.LibraryLogRepository;
import com.adityabansal7.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LibraryLogServiceTest {

	@Mock
	LibraryLogRepository libraryLogRepo;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	BookRepository bookRepo;
	
	@InjectMocks
	LibraryLogServiceImpl libraryLogService;
	
	User user;
	Book book;
	LibraryLog libraryLog;
	@BeforeEach
	public void setUp() {
	   user=User.builder()
			    .userId(1)
			    .firstName("Manjunath")
			    .lastName("patil")
			    .address("chikodi")
			    .userEmail("patil@gmail.com")
			    .phoneNumber("7019478133")
			    .bookList(new ArrayList<>())
			    .build();
	   
	   book = Book.builder()
			   .bookId(2)
			   .bookAuthor("Manjunath")
			   .bookTitle("SpringBoot")
			   .availability(true)
			   .build();
	   
	   libraryLog = LibraryLog.builder()
			                  .userId(user.getUserId())
			                  .bookId(book.getBookId())
			                  .bookIssueDate(LocalDate.now())
			                  .bookDueDate(LocalDate.now().plusDays(8))
			                  .bookReturnDate(null)
			                  .Fine(0)
			                  .build();
	}
	
	@Test
	public void givenUserIdAndBookId_whenIssueBook_thenReturnDueDate() throws UserException, BookException{
		//give setup
		BDDMockito.given(userRepo.findById(user.getUserId())).willReturn(Optional.of(user));
		BDDMockito.given(bookRepo.findById(book.getBookId())).willReturn(Optional.of(book));
		user.getBookList().add(book);
		BDDMockito.given(userRepo.save(user)).willReturn(user);
		BDDMockito.given(libraryLogRepo.save(libraryLog)).willReturn(libraryLog);
		
		//when
		String message = libraryLogService.issueBook(user.getUserId(), book.getBookId());
		
		//Then verify the message.
		Assertions.assertThat(message).isNotNull();
		Assertions.assertThat(message).isEqualTo("Last date to submit the book is "+ LocalDate.now().plusDays(8));
	}
	
	
	@Test
	public void givenUserIdBookIdReturnDate_whenReturnBook_thenShowMessage() throws UserException, BookException {
		//Given - setUp
		BDDMockito.given(userRepo.findById(user.getUserId())).willReturn(Optional.of(user));
		BDDMockito.given(bookRepo.findById(book.getBookId())).willReturn(Optional.of(book));
		user.getBookList().add(book);
		BDDMockito.given(userRepo.save(user)).willReturn(user);
		BDDMockito.given(libraryLogRepo.findByUserIdAndBookId(user.getUserId(), book.getBookId()))
		                               .willReturn(Optional.of(libraryLog));
		
		LibraryLog savedLLibraryLog = LibraryLog.builder()
										        .userId(user.getUserId())
										        .bookId(book.getBookId())
										        .bookIssueDate(LocalDate.now())
										        .bookDueDate(LocalDate.now().plusDays(8))
										        .bookReturnDate(LocalDate.now().plusDays(6))
										        .Fine(0)
										        .build();
		
		BDDMockito.given(libraryLogRepo.save(libraryLog)).willReturn(savedLLibraryLog);
		
		//When return book.
		String message = libraryLogService.returnBook(user.getUserId(), book.getBookId(), LocalDate.now().plusDays(6));
		
		//Then verify the message.
		Assertions.assertThat(message).isNotNull();
		Assertions.assertThat(message).isEqualTo("Book is returned before due date");
	}
	
	
}

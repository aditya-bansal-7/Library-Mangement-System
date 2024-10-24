package com.adityabansal7.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adityabansal7.model.Book;
import com.adityabansal7.model.User;
import com.adityabansal7.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	BookServiceImpl bookService;
	
    Book book;
	
	@BeforeEach
	public void setUp() {
		book = Book.builder()
				   .bookAuthor("Manjunath")
				   .bookTitle("SpringBoot")
				   .availability(true)
				   .build();
	}
	
	@Test
	public void givenBookObject_whenSaveBook_ThenReturnSavedBook(){
		
		BDDMockito.given(bookRepository.save(book)).willReturn(book);
		
		//when saved object.
		Book savedBook = bookService.addBook(book);
		
		//Then verify the content.
		Assertions.assertThat(savedBook).isNotNull();
		Assertions.assertThat(savedBook.getBookTitle()).isEqualTo("SpringBoot");
	}
	
	@Test
	public void givenBookId_whenFindById_thenReturnBookObject() {
		
		BDDMockito.given(bookRepository.findById(book.getBookId())).willReturn(Optional.of(book));
		
		//when findByUser is called.
		Optional<Book> bookOptional = bookService.findBookById(book.getBookId());
				
		//Then verify the result object.
		Assertions.assertThat(bookOptional.get()).isNotNull();
	}

}

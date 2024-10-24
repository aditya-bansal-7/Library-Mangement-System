package com.adityabansal7.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.adityabansal7.model.Book;
import com.adityabansal7.model.User;

@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepo;
	
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
		
		//when saved object.
		Book savedBook = bookRepo.save(book);
		
		//Then verify the content.
		Assertions.assertThat(savedBook).isNotNull();
		Assertions.assertThat(savedBook.getBookTitle()).isEqualTo("SpringBoot");
	}
	
	@Test
	public void givenBookId_whenFindById_thenReturnBookObject() {
		
		bookRepo.save(book);
		//when findById.
		Book savedBook = bookRepo.findById(book.getBookId()).get();
		
		//Then verify the saved object.
		Assertions.assertThat(savedBook).isNotNull();
	}
	
	
}

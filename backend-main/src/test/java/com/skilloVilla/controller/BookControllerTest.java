package com.adityabansal7.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Optional;

import org.hamcrest.CoreMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.adityabansal7.model.Book;
import com.adityabansal7.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@MockBean
    private BookService bookService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenBookObject_whenSaveBook_thenReturnSavedBook() throws Exception {
	  //given - precondition or setup	
	  Book book = Book.builder()
				   .bookAuthor("Manjunath")
				   .bookTitle("SpringBoot")
				   .availability(true)
				   .build();
	  BDDMockito.given(bookService.addBook(ArgumentMatchers.any(Book.class)))
	                              .willAnswer((invocation) ->invocation.getArgument(0));
	  
	  //when - action or behaviour under test
	  ResultActions response = mockMvc.perform(post("/book/addNewBook")
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(objectMapper.writeValueAsString(book)));
	  
	  //then - verify the results or output using assert statement
	  response.andExpect(MockMvcResultMatchers.status().isCreated())
	          .andExpect(MockMvcResultMatchers.jsonPath("$.bookTitle", CoreMatchers.is(book.getBookTitle())))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.bookAuthor", CoreMatchers.is(book.getBookAuthor())));
	          
	}
	
	@Test
	public void givenBookId_whenFindById_thenReturnSavedBook() throws Exception {
		//given - precondition or setup
		int bookId = 1;
		Book book = Book.builder()
				   .bookAuthor("Manjunath")
				   .bookTitle("SpringBoot")
				   .availability(true)
				   .build();
		BDDMockito.given(bookService.findBookById(bookId)).willReturn(Optional.of(book));
		
		//when - action or behaviour under test
		ResultActions response = mockMvc.perform(get("/book/getBook/{bookId}", bookId));
		
		//then - verify the results or output using assert statement
		response.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.bookTitle", CoreMatchers.is(book.getBookTitle())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.bookAuthor", CoreMatchers.is(book.getBookAuthor())));
	}
	
	
}

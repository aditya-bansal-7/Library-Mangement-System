package com.adityabansal7.controller;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.adityabansal7.exception.BookException;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.service.LibraryLogService;

@WebMvcTest(LibraryLogController.class)
public class LibraryLogControllerTest {

	@MockBean
	LibraryLogService libraryLogService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenUserIdBookId_whenIssueBook_thenReturnDueDate() throws Exception {
		//given - precondition or setup	
		int bookId = 2;
		int userId = 1;
		
		BDDMockito.given(libraryLogService.issueBook(bookId, userId))
		          .willReturn("Last date to submit the book is "+ LocalDate.now().plusDays(8));
		
		//when - action or behaviour under test
		ResultActions response = mockMvc.perform(get("/libraryLog/issueBook/userId/{userId}/bookId/{bookId}", userId,bookId));
		
		//then - verify the results or output using assert statement
		response.andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
}

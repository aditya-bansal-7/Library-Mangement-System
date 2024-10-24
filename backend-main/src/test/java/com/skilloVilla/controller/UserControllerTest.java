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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;
import com.adityabansal7.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

	@MockBean
	UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenUserObject_whenSaveUser_thenReturnSavedUser() throws Exception {
		//given - precondition or setup
		User user = User.builder()
					    .userId(1)
					    .firstName("Manjunath")
					    .lastName("patil")
					    .address("chikodi")
					    .userEmail("patil@gmail.com")
					    .phoneNumber("7019478133")
					    .bookList(null)
					    .build();
		BDDMockito.given(userService.addUser(ArgumentMatchers.any(User.class)))
		                            .willAnswer((invocation) -> invocation.getArgument(0));
		
		//when - action or behaviour under test
		ResultActions response = mockMvc.perform(post("/user/registerUser")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)));
		
		//then - verify the results or output using assert statement.
		response.andExpect(MockMvcResultMatchers.status().isCreated())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail", CoreMatchers.is(user.getUserEmail())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(user.getPhoneNumber())));
	}
	
	@Test
	public void givenUserId_whenFindUserById_thenReturnSavedUser() throws Exception {
		//given - precondition or setup
		User user = User.builder()
			    .userId(1)
			    .firstName("Manjunath")
			    .lastName("patil")
			    .address("chikodi")
			    .userEmail("patil@gmail.com")
			    .phoneNumber("7019478133")
			    .bookList(null)
			    .build();
		BDDMockito.given(userService.findUserById(user.getUserId())).willReturn(Optional.of(user));		
		
		//when - action or behaviour under test
		ResultActions response = mockMvc.perform(get("/user/getUser/{userId}", user.getUserId()));
		
		//then - verify the results or output using assert statement.
		response.andExpect(MockMvcResultMatchers.status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail", CoreMatchers.is(user.getUserEmail())))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(user.getPhoneNumber())));
	}
	
}

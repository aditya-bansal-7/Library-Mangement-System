package com.adityabansal7.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;
import com.adityabansal7.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepo;
	
	@InjectMocks
	UserServiceImpl userService;
	
    User user;
	
	@BeforeEach
	public void setUp() {
	   user=User.builder()
			    .userId(1)
			    .firstName("Manjunath")
			    .lastName("patil")
			    .address("chikodi")
			    .userEmail("patil@gmail.com")
			    .phoneNumber("7019478133")
			    .bookList(null)
			    .build();
	}
	
	@Test
	public void givenUserObject_whenSaveUser_thenReturnSavedUser() throws UserException {
		//give setup
		BDDMockito.given(userRepo.findByUserEmail(user.getUserEmail())).willReturn(Optional.empty());
		BDDMockito.given(userRepo.save(user)).willReturn(user);
		
		//when addUser is called
		User savedUser = userService.addUser(user);
		
		//Then verify the result object.
		Assertions.assertThat(savedUser).isNotNull();
	}
	
	@Test
	public void givenExistingUserObject_whenSaveUser_thenThrowException() throws UserException {
		//give setup
		BDDMockito.given(userRepo.findByUserEmail(user.getUserEmail())).willReturn(Optional.of(user));
		
		//when addUser is called
		org.junit.jupiter.api.Assertions.assertThrows(UserException.class, () ->{
			userService.addUser(user);
		});
		
		//Then verify the result object.
		verify(userRepo, never()).save(any(User.class));
	}
	
	@Test
	public void givenUserId_whenfindById_thenReturnSavedUser() {
		//give setup
		BDDMockito.given(userRepo.findById(user.getUserId())).willReturn(Optional.of(user));
		
		//when findByUser is called.
		Optional<User> userOptional = userService.findUserById(user.getUserId());
		
		//Then verify the result object.
		Assertions.assertThat(userOptional.get()).isNotNull();
	}
	
	
}

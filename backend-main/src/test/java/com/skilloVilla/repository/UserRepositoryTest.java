package com.adityabansal7.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.adityabansal7.model.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepo;
	
	User user;
	
	@BeforeEach
	public void setUp() {
	   user=User.builder()
			    .firstName("Manjunath")
			    .lastName("patil")
			    .address("chikodi")
			    .userEmail("patil@gmail.com")
			    .phoneNumber("7019478133")
			    .bookList(null)
			    .build();
	}
	
	@Test
	public void givenUserObject_whenSaveUser_ThenReturnSavedUser(){
		
		//when saved object.
		User savedUser = userRepo.save(user);
		
		//Then verify the content.
		Assertions.assertThat(savedUser).isNotNull();
		Assertions.assertThat(savedUser.getFirstName()).isEqualTo("Manjunath");
	}
	
	@Test
	public void givenUserId_whenFindById_thenReturnUserObject() {
		
		userRepo.save(user);
		//when findById.
		User savedUser = userRepo.findById(user.getUserId()).get();
		
		//Then verify the saved object.
		Assertions.assertThat(savedUser).isNotNull();
	}
}

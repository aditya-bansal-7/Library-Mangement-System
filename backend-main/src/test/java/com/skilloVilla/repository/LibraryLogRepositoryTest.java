package com.adityabansal7.repository;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.adityabansal7.model.LibraryLog;

@DataJpaTest
public class LibraryLogRepositoryTest {

	@Autowired
	LibraryLogRepository libraryLogRepo;
	
	@Test
	public void givenDummyEntry_whenSaveIntoLog_thenReturnSavedEntry() {
		//Given - setup
		LibraryLog libraryLog = LibraryLog.builder()
				                          .userId(1)
				                          .bookId(2)
				                          .bookDueDate(LocalDate.now().plusDays(8))
				                          .bookIssueDate(LocalDate.now())
				                          .build();
		//when save the object.
		LibraryLog savedLibraryLog = libraryLogRepo.save(libraryLog);
		
		//Then verify the saved object.
		Assertions.assertThat(savedLibraryLog).isNotNull();
		Assertions.assertThat(savedLibraryLog.getBookId()).isNotEqualTo(0);
	}
	
	
}

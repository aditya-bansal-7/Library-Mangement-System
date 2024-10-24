package com.adityabansal7.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adityabansal7.model.LibraryLog;

public interface LibraryLogRepository extends JpaRepository<LibraryLog, Integer>{

	@Query("select l from  LibraryLog l where l.userId = ?1 and l.bookId = ?2 and l.bookReturnDate = null")
	public Optional<LibraryLog> findByUserIdAndBookId(Integer userId, Integer bookId);
	
}

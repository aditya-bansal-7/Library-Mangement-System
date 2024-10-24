package com.adityabansal7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adityabansal7.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(b.bookAuthor) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooks(@Param("query") String query);
}

package com.adityabansal7.service;

import java.util.List;
import java.util.Optional;

import com.adityabansal7.model.Book;
import com.adityabansal7.model.Review;

public interface BookService {
	public Book addBook(Book book);
	public Optional<Book> findBookById(Integer bookId);
	public List<Book> searchBooks(String query, Integer userId);
	public List<Book> getAllBooks();
	public Book addReview(Integer bookId, Review review);
	public Book toggleAvailability(Integer bookId, boolean availability); // Ensure this method is defined
}

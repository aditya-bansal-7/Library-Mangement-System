package com.adityabansal7.service;

import java.util.List;
import java.util.Optional;

import com.adityabansal7.model.Book;

public interface BookService {
	public Book addBook(Book book);
	public Optional<Book> findBookById(Integer bookId);
	public List<Book> searchBooks(String query, Integer userId);
	public List<Book> getAllBooks();
}

package com.adityabansal7.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adityabansal7.model.Book;
import com.adityabansal7.repository.BookRepository;
import com.adityabansal7.model.Review;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Book addBook(Book book){
		
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> findBookById(Integer bookId){
	 	return bookRepository.findById(bookId);
	}

	@Override
	public List<Book> searchBooks(String query, Integer userId) {
		// You can use the userId to implement user-specific search logic if needed
		return bookRepository.searchBooks(query);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book addReview(Integer bookId, Review review) {
		Optional<Book> optionalBook = findBookById(bookId);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			book.getReviews().add(review); 
			return bookRepository.save(book);
		}
		throw new RuntimeException("Book not found");
	}

	@Override
	public Book toggleAvailability(Integer bookId, boolean availability) {
		Optional<Book> optionalBook = findBookById(bookId);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			book.setAvailability(availability);
			return bookRepository.save(book);
		}
		throw new RuntimeException("Book not found");
	}

}

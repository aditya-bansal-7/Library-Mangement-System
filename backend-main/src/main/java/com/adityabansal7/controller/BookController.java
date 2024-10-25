package com.adityabansal7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adityabansal7.model.Book;
import com.adityabansal7.model.Review;
import com.adityabansal7.service.BookService;

// Add this import statement
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {
 
	@Autowired
	BookService bookService;
	
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@PostMapping("/addNewBook")
	public ResponseEntity<Book> addNewBook(@RequestBody Book book){
	    Book savedBook =  bookService.addBook(book);
	    return new ResponseEntity<Book>(savedBook,HttpStatus.CREATED);
	}
	
	@GetMapping("/getBook/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer bookId){
		return bookService.findBookById(bookId)
				          .map(ResponseEntity::ok)
				          .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(@RequestBody SearchRequest searchRequest) {
		logger.info("Received search request: {}", searchRequest);
		List<Book> searchResults = bookService.searchBooks(searchRequest.getQuery(), searchRequest.getUser());
		logger.info("Search results: {}", searchResults);
		return ResponseEntity.ok(searchResults);
	}
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allBooks = bookService.getAllBooks();
		return ResponseEntity.ok(allBooks);
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBookDetails(@PathVariable Integer bookId) {
		return bookService.findBookById(bookId)
			          .map(ResponseEntity::ok)
			          .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping("/book/{bookId}/addReview")
	public ResponseEntity<Book> addReview(@PathVariable Integer bookId, @RequestBody Review review) {
		logger.info("Received review for bookId {}: {}", bookId, review);
		review.setUserId(review.getUserId()); 
		review.setUserName(review.getUserName());
		Book updatedBook = bookService.addReview(bookId, review);
		return ResponseEntity.ok(updatedBook);
	}
	
	@PostMapping("/{bookId}/toggleAvailability")
	public ResponseEntity<Book> toggleAvailability(@PathVariable Integer bookId, @RequestBody Map<String, Boolean> availability) {
		Book book = bookService.findBookById(bookId)
				.orElseThrow(() -> new RuntimeException("Book not found"));
		book.setAvailability(availability.get("availability"));
		Book updatedBook = bookService.addBook(book);
		return ResponseEntity.ok(updatedBook);
	}
}

// Add this class at the end of the file
class SearchRequest {
	private String query;
	private Integer user;

	// Getters and setters
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}
}

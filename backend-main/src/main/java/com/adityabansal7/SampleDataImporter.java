package com.adityabansal7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.adityabansal7.model.User;
import com.adityabansal7.model.Book;
import com.adityabansal7.model.Review;
import com.adityabansal7.repository.UserRepository;
import com.adityabansal7.repository.BookRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SampleDataImporter {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SampleDataImporter.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);

        // Create and save sample users
        User user1 = User.builder()
                .firstName("Student")
                .lastName("1")
                .address("123 Main St")
                .phoneNumber("1234567890")
                .userEmail("student1@galgotiasuniversity.ac.in")
                .password("Pass@123")
                .isAdmin(false)
                .studentId("STU001")
                .userType("student")
                .isActive(true)
                .isBorrowingBlocked(false)
                .fineAmount(0.0)
                .build();

        User user2 = User.builder()
                .firstName("Admin")
                .lastName("1")
                .address("456 Elm St")
                .phoneNumber("0987654321")
                .userEmail("admin1@galgotiasuniversity.ac.in")
                .password("Pass@123")
                .isAdmin(true)
                .studentId("ADM001")
                .userType("staff")
                .isActive(true)
                .isBorrowingBlocked(false)
                .fineAmount(0.0)
                .build();

        User user3 = User.builder()
                .firstName("Faculty")
                .lastName("1")
                .address("789 Oak St")
                .phoneNumber("1122334455")
                .userEmail("faculty1@galgotiasuniversity.ac.in")
                .password("Pass@123")
                .isAdmin(false)
                .studentId("FAC001")
                .userType("faculty")
                .isActive(true)
                .isBorrowingBlocked(false)
                .fineAmount(0.0)
                .build();

        // Save users
        userRepository.saveAll(Arrays.asList(user1, user2, user3));

        // Create sample reviews
        Review review1 = Review.builder()
                .message("Great book for learning Spring Boot!")
                .userId(user1.getUserId())
                .build();

        Review review2 = Review.builder()
                .message("Very informative and well-written.")
                .userId(user2.getUserId())
                .build();

        // Create and save sample books
        Book book1 = Book.builder()
                .bookTitle("Spring Boot in Action")
                .bookAuthor("Craig Walls")
                .description("A comprehensive guide to Spring Boot.")
                .tags("Spring, Java, Boot")
                .availability(true)
                .reviews(Arrays.asList(review1))
                .build();

        Book book2 = Book.builder()
                .bookTitle("Java Concurrency in Practice")
                .bookAuthor("Brian Goetz")
                .description("An in-depth look at Java concurrency.")
                .tags("Java, Concurrency, Multithreading")
                .availability(true)
                .reviews(Arrays.asList(review2))
                .build();

        // Save books
        bookRepository.saveAll(Arrays.asList(book1, book2));

        System.out.println("Sample data saved to the database.");
    }
}

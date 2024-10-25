package com.adityabansal7.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bookId;
	private String bookTitle;
	private String bookAuthor;
	private boolean availability;
	private String description;
	private String tags; // Comma-separated tags

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Review> reviews;

}

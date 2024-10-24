package com.adityabansal7.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer logId;
	private Integer userId;
	private Integer bookId;
	private LocalDate bookIssueDate;
	private LocalDate bookDueDate;
	private LocalDate bookReturnDate;
	private Integer Fine;
}

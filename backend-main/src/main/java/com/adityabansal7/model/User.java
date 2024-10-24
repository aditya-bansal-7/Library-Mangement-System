package com.adityabansal7.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String address;

    @Column(unique=true)
    private String phoneNumber;

    @Column(unique=true)
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> bookList;

    @PrePersist
    @PreUpdate
    private void validateEmail() {
        if (userEmail == null || !userEmail.toLowerCase().endsWith("@galgotiasuniversity.ac.in")) {
            throw new IllegalArgumentException("Invalid email domain. Please use a Galgotias University email address.");
        }
    }
}

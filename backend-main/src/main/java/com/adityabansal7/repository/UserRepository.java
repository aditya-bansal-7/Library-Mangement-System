package com.adityabansal7.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adityabansal7.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByUserEmail(String email);
}

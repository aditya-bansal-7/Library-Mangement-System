package com.adityabansal7.service;

import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;

import java.util.Optional;

public interface UserService {
    public User addUser(User user) throws UserException;
    public Optional<User> findUserById(Integer userId);
    public User loginUser(String email, String password) throws UserException;
}

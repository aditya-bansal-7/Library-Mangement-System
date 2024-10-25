package com.adityabansal7.service;

import java.util.List;
import java.util.Optional;
import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;

public interface UserService {
    User addUser(User user) throws UserException;
    Optional<User> findUserById(Integer userId);
    User loginUser(String email, String password) throws UserException;

    List<User> getAllUsers(); 
    User blockUser(Integer userId) throws UserException;
    User unblockUser(Integer userId) throws UserException; 

    User updateFine(Integer userId, Double fine) throws UserException;
}

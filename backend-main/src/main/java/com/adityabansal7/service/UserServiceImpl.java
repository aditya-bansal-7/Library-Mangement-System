package com.adityabansal7.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adityabansal7.exception.UserException;
import com.adityabansal7.model.User;
import com.adityabansal7.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User addUser(User user) throws UserException {
        // Add email validation
        if (!isValidGalgotiasEmail(user.getUserEmail())) {
            throw new UserException("Invalid email domain. Please use a Galgotias University email address.");
        }

        Optional<User> userOptional = userRepo.findByUserEmail(user.getUserEmail());
        if (userOptional.isPresent()) 
            throw new UserException("This user is already registered");
        else {
            // Ensure the password is set before saving
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new UserException("Password cannot be empty");
            }
            // Consider hashing the password before saving
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            return userRepo.save(user);
        }
    }

    @Override
    public Optional<User> findUserById(Integer userId) {
        return userRepo.findById(userId);
    }

    @Override
    public User loginUser(String email, String password) throws UserException {
        // Remove the email validation here as we'll validate it in the User model
        Optional<User> userOptional = userRepo.findByUserEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserException("User not found with email: " + email);
        }
        
        User user = userOptional.get();

        if (!user.getPassword().equals(password)) {
            throw new UserException("Invalid credentials");
        }
        
        return user;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User blockUser(Integer userId) throws UserException {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User not found"));
        user.blockUser();
        return userRepo.save(user);
    }

    @Override
    public User unblockUser(Integer userId) throws UserException {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User not found"));
        user.unblockUser();
        return userRepo.save(user);
    }

    @Override
    public User updateFine(Integer userId, Double fine) throws UserException {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserException("User not found"));
        user.setFine(fine);
        return userRepo.save(user);
    }
    
    private boolean isValidGalgotiasEmail(String email) {
        return email != null && email.toLowerCase().endsWith("@galgotiasuniversity.ac.in");
    }
}

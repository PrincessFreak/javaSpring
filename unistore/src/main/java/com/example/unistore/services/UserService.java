package com.example.unistore.services;

import com.example.unistore.models.User;
import com.example.unistore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    public final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUserLogin(User user) {
        Optional<User> user_db = userRepository.findByUserLogin(user.getUserLogin());
        return user_db.orElse(null);
    } // find user by login

    @Transactional
    public void registrationUser(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    } // save user in db
}

package com.example.unistore.services;

import com.example.unistore.models.User;
import com.example.unistore.repositories.UserRepository;
import com.example.unistore.security.UserDetailes;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserLogin(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не существует");
        }
        return new UserDetailes(optionalUser.get());
    }
}

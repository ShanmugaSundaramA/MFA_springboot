package com.microsoft.azuresamples.msal4j.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microsoft.azuresamples.msal4j.model.User;
import com.microsoft.azuresamples.msal4j.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

     private final UserRepository userRepository;

     public Optional<User> findByEmail(String email) {
          return userRepository.findByEmail(email);
     }

     public User save(User user) {
          return userRepository.save(user);
     }

}

package com.microsoft.azuresamples.msal4j.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microsoft.azuresamples.msal4j.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

     Optional<User> findByEmail(String email);
}

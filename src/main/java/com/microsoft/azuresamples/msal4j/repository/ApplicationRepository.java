package com.microsoft.azuresamples.msal4j.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microsoft.azuresamples.msal4j.model.Application;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {

     Optional<Application> findByAppName(String appName);
}

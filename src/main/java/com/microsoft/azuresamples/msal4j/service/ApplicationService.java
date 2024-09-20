package com.microsoft.azuresamples.msal4j.service;

import org.springframework.stereotype.Service;

import com.microsoft.azuresamples.msal4j.model.Application;
import com.microsoft.azuresamples.msal4j.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {

     private final ApplicationRepository applicationRepository;

     public Application findByAppName(
               String appName) throws Exception {

          return applicationRepository.findByAppName(appName)
                    .orElseThrow(() -> new Exception("Application does not exist."));
     }
}

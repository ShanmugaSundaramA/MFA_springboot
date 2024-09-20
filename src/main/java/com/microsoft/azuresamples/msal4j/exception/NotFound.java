package com.microsoft.azuresamples.msal4j.exception;

public class NotFound extends RuntimeException {

     private static final long serialVersionUID = 1L;

     public NotFound(String message) {
          super(message);
     }
}
package com.microsoft.azuresamples.msal4j.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "applications")
public class Application {

     @Id
     private String id;
     private String appName;
     private String url;
     private String logo;
     @CreatedDate
     private Date createdAt;
     @LastModifiedDate
     private Date updatedAT;
}

package com.microsoft.azuresamples.msal4j.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User implements UserDetails {

     private static final long serialVersionUID = 1L;

     @Id
     private String id;
     private String email;
     private String role;
     @CreatedDate
     private Date createdAt;
     @LastModifiedDate
     private Date updatedAt;
     @JsonIgnore
     private String password;
     @JsonIgnore
     private String authorities;
     @JsonIgnore
     private String enabled;
     @JsonIgnore
     private String username;
     @JsonIgnore
     private String credentialsNonExpired;
     @JsonIgnore
     private String accountNonExpired;
     @JsonIgnore
     private String accountNonLocked;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return new ArrayList<>();
     }

     @Override
     public String getUsername() { // NOSONAR
          return email;
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }

     @Override
     public String getPassword() {
          return null;
     }
}

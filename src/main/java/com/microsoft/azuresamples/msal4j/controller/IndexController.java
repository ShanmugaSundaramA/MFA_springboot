package com.microsoft.azuresamples.msal4j.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.microsoft.azuresamples.msal4j.model.Application;
import com.microsoft.azuresamples.msal4j.model.User;
import com.microsoft.azuresamples.msal4j.service.ApplicationService;
import com.microsoft.azuresamples.msal4j.service.JwtService;
import com.microsoft.azuresamples.msal4j.service.UserService;
import com.nimbusds.jose.shaded.json.JSONArray;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class IndexController {

     @Value("${azure.activedirectory.b2c.redirect.uri}")
     private String redirectURI;

     private final ApplicationService applicationService;
     private final JwtService jwtService;
     private final UserService userService;

     @GetMapping("/redirect")
     public RedirectView signin(
               @AuthenticationPrincipal OidcUser oidcUser,
               @RequestParam String appName,
               @RequestParam String token) throws Exception {

          Application application = applicationService.findByAppName(appName);
          JSONArray emailArr = oidcUser.getAttribute("emails");
          String email = (String) emailArr.get(0);
          Optional<User> userOpt = userService.findByEmail(email);
          if (userOpt.isEmpty()) {
               User user = User.builder()
                         .email(email)
                         .build();
               userService.save(user);
          }
          // String token = jwtService.generateToken(email);
          return new RedirectView(application.getUrl() + "?token=" + token + "&email=" + email);
     }

     @GetMapping("/token")
     public RedirectView getToken(
               @RequestParam String appName,
               @AuthenticationPrincipal OidcUser oidcUser) throws Exception {

          Application application = applicationService.findByAppName(appName);
          JSONArray emailArr = oidcUser.getAttribute("emails");
          String email = (String) emailArr.get(0);

          Optional<User> userOpt = userService.findByEmail(email);
          if (userOpt.isEmpty()) {
               User user = User.builder()
                         .email(email)
                         .build();
               userService.save(user);
          }
          String token = jwtService.generateToken(email);
          return new RedirectView(application.getUrl() + "?token=" + token + "&email=" + email);
     }
}

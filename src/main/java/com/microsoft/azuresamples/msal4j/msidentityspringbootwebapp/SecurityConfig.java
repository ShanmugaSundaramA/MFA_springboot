// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.azuresamples.msal4j.msidentityspringbootwebapp;

import com.azure.spring.autoconfigure.b2c.AADB2COidcLoginConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.protect.authenticated}")
    private String[] protectedRoutes;

    private final AADB2COidcLoginConfigurer configurer;

    public SecurityConfig(AADB2COidcLoginConfigurer configurer) {
        this.configurer = configurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests(requests -> requests
                .antMatchers(protectedRoutes).authenticated()
                .antMatchers("/**").permitAll())
                .apply(configurer);
    }

}
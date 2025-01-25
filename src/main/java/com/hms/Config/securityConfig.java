package com.hms.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.ui.ModelMap;

@Configuration
public class securityConfig {

    private JWTFilter jf;

    public securityConfig(JWTFilter jf) {
        this.jf = jf;
    }

    @Bean
    public ModelMapper mm(){
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain SFC(
            HttpSecurity hs
    ) throws Exception {
        hs.csrf().disable()
                .cors().disable();

//        hs.authorizeHttpRequests().requestMatchers("/security/signup","/security/login",
//                        "/security/property/signup","/Property/{search}")
//                .permitAll().requestMatchers("/Property/addPro").hasRole("OWNER")
//                .requestMatchers("/Property/deletePro").hasAnyRole("OWNER","ADMIN")
//                .requestMatchers("/Review/add").hasAnyRole("USER","ADMIN").anyRequest().authenticated();

        hs.authorizeHttpRequests().anyRequest().permitAll();

        hs.addFilterBefore(jf, AuthorizationFilter.class);
        return hs.build();
    }
}

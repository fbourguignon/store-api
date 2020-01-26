package com.store.config;

import com.store.security.UserPrincipal;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.UUID;

@TestConfiguration
public class SpringSecurityConfigTest {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserPrincipal simpleUser = UserPrincipal
                .builder()
                .id(UUID.fromString("7e7fb1b6-07a2-493d-85a7-6799afbdca29"))
                .name("Simple User")
                .username("simple_user@store.com.br")
                .password("$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO")
                .authorities(
                        Arrays.asList(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                )
                .build();

        UserPrincipal adminUser = UserPrincipal
                .builder()
                .id(UUID.fromString("31abebd1-d1af-4cde-8c55-d5a6e21f2110"))
                .name("Admin user")
                .username("admin_user@store.com.br")
                .password("$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO")
                .authorities(
                        Arrays.asList(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        )
                )
                .build();

        return new InMemoryUserDetailsManager(Arrays.asList(adminUser,simpleUser));
    }
}

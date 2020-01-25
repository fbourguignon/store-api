package com.store.unit.service;

import com.store.model.Role;
import com.store.model.User;
import com.store.service.CustomUserDetailsService;
import com.store.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

    private static final UUID USER_ID = UUID.fromString("43d843d3-9561-42c0-ae04-f0d5ab83dee4");
    private static final String USER_MAIL = "jhon@gmail.com";
    private static final String USER_PASSWORD_ENCODED = "$2a$10$oYjYwYZ41S3qCKgPm.uE.5KRiDg4E9QNewUO";

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserService userService;

    @Test
    public void loadByUsernameMustCallUserService(){
        User user = mockUser();
        doReturn(user).when(userService).findUserByEmail(USER_MAIL);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(USER_MAIL);

        verify(userService, times(1)).findUserByEmail(USER_MAIL);
        assertEquals(userDetails.getUsername(),user.getMail());
        assertEquals(userDetails.getUsername(),user.getMail());
    }

    @Test
    public void loadByIdMustCallUserService(){
        User user = mockUser();
        doReturn(user).when(userService).findUserById(USER_ID);

        UserDetails userDetails = customUserDetailsService.loadUserById(USER_ID);

        verify(userService, times(1)).findUserById(USER_ID);
        assertEquals(userDetails.getUsername(),user.getMail());
        assertEquals(userDetails.getPassword(),user.getPassword());


    }


    private User mockUser(){


        Set<Role> roles = new HashSet<Role>();

        User user = User.builder()
                .id(USER_ID)
                .mail(USER_MAIL)
                .password(USER_PASSWORD_ENCODED)
                .roles(roles)
                .build();

        return user;
    }

}

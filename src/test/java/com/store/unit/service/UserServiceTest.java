package com.store.unit.service;

import com.store.dto.RegisterRequestDTO;
import com.store.enumerator.RoleType;
import com.store.model.Role;
import com.store.model.User;
import com.store.repository.RoleRepository;
import com.store.repository.UserRepository;
import com.store.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final UUID USER_ID = UUID.fromString("43d843d3-9561-42c0-ae04-f0d5ab83dee4");
    private static final String USER_MAIL = "jhon@gmail.com";
    private static final String USER_PASSWORD_ENCODED = "$2a$10$oYjYwYZ41S3qCKgPm.uE.5KRiDg4E9QNewUO";
    private static final String USER_PASSWORD_DECODED = "123456";

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void findByUserIdMustCallRepository(){

        doReturn(Optional.of(new User().builder().id(USER_ID).build())).when(userRepository).findById(USER_ID);

        User user = userService.findUserById(USER_ID);

        verify(userRepository, times(1)).findById(USER_ID);
        assertNotNull(user);
    }

    @Test
    public void findByUserMailMustCallRepository(){

        doReturn(Optional.of(new User().builder().name(USER_MAIL).build())).when(userRepository).findByMail(USER_MAIL);

        User user = userService.findUserByEmail(USER_MAIL);

        verify(userRepository, times(1)).findByMail(USER_MAIL);
        assertNotNull(user);
    }

    @Test
    public void saveUserMustCallRepository(){

        RegisterRequestDTO registerRequest = new RegisterRequestDTO().builder().mail(USER_MAIL).password(USER_PASSWORD_DECODED).build();
        doReturn(Optional.of(new Role().builder().type(RoleType.ROLE_USER).build())).when(roleRepository).findByType(RoleType.ROLE_USER);
        doReturn(USER_PASSWORD_ENCODED).when(passwordEncoder).encode(USER_PASSWORD_DECODED);

        userService.createUser(registerRequest);

        verify(roleRepository, times(1)).findByType(RoleType.ROLE_USER);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertNotNull(capturedUser.getRoles());
        assertEquals(USER_PASSWORD_ENCODED,capturedUser.getPassword());
        assertEquals(USER_MAIL,capturedUser.getMail());

    }
}

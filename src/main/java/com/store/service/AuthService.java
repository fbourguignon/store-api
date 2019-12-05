package com.store.service;


import com.store.dto.LoginRequestDTO;
import com.store.dto.LoginResponseDTO;
import com.store.exception.StoreInvalidCredentialsException;
import com.store.security.JwtTokenProvider;
import com.store.security.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AuthService {

    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider tokenProvider;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var userPrincipal = (UserPrincipal) authentication.getPrincipal();
            var jwt = tokenProvider.generateToken(authentication);

            return LoginResponseDTO.builder()
                    .accessToken(jwt)
                    .name(userPrincipal.getName())
                    .email(userPrincipal.getUsername())
                    .id(userPrincipal.getId())
                    .build();
        } catch (BadCredentialsException bd) {
            throw new StoreInvalidCredentialsException("Usuário/Senha inválidos!");
        } catch (Exception e) {
            throw e;
        }
    }
}

package com.store.service;



import com.store.dto.RegisterRequestDTO;
import com.store.enumerator.RoleType;
import com.store.exception.StoreBusinessException;
import com.store.exception.StoreGenericException;
import com.store.model.Role;
import com.store.model.User;
import com.store.repository.RoleRepository;
import com.store.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Log4j2
@Service
public class UserService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User findUserByEmail(String mail) {
        return userRepository.findByMail(mail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado : " + mail)
                );
    }

    public User findUserById(UUID id){
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado com id : " + id)
        );
    }

    public User createUser(RegisterRequestDTO request){
        return save(request,RoleType.ROLE_USER);
    }

    public User createAdminUser(RegisterRequestDTO request){
        return save(request,RoleType.ROLE_ADMIN);
    }

    private User save(RegisterRequestDTO request,RoleType roleType) {
        try{
            User user = User.builder()
                    .name(request.getName())
                    .lastName(request.getLastName())
                    .mail(request.getMail())
                    .password(request.getPassword())
                    .build();

            Role userRole = roleRepository.findByType(roleType)
                    .orElseThrow(() -> new StoreGenericException("Role não localizada"));

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRoles(Collections.singleton(userRole));

            return userRepository.save(user);
        }catch (DataIntegrityViolationException de) {
            log.error("O email [{}] já existe em nossos registros",request.getMail());
            throw new StoreBusinessException("Email já cadastrado!");
        }catch (Exception e){
            log.error("Houve um erro ao realizar o cadastro");
            throw new StoreGenericException("Houve um erro ao realizar o cadastro");
        }
    }


}

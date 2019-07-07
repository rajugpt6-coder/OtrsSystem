package com.techment.OtrsSystem.Service;

import com.techment.OtrsSystem.Repository.RoleRepository;
import com.techment.OtrsSystem.Repository.UserRepository;
import com.techment.OtrsSystem.Security.JwtProvider;
import com.techment.OtrsSystem.domain.Role;
import com.techment.OtrsSystem.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

//    @Autowired
//    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
//                       RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
//        this.userRepository = userRepository;
//        this.authenticationManager = authenticationManager;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtProvider = jwtProvider;
//    }

    /**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param username  username
     * @param password  password
     * @return Optional of the Java Web Token, empty otherwise
     */
    public Optional<String> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isPresent()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e){
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    /**
     * Create a new user in the database.
     *
     * @param username username
     * @param password password
     * @param firstName first name
     * @param lastName last name
     * @return Optional of user, empty if the user already exists.
     */
    public Optional<User> signup(String username, String password, String firstName, String lastName, String middleName, String phoneNo) {
        LOGGER.info("New user attempting to sign up");
        Optional<User> user = Optional.empty();

        if (!userRepository.findByEmail(username).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_ADMIN");
            user = Optional.of(userRepository.save(new User(username,
                    passwordEncoder.encode(password),
                    firstName,
                    middleName,
                    lastName,
                    phoneNo,
                    role.get()
                   )));
        }
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Boolean isExist(long id){
        return userRepository.existsById(id);
    }
}

package com.pangan.mongodbsecurity;

import com.pangan.mongodbsecurity.dto.RegisterDto;
import com.pangan.mongodbsecurity.model.Role;
import com.pangan.mongodbsecurity.model.UserAuth;
import com.pangan.mongodbsecurity.repository.RoleRepository;
import com.pangan.mongodbsecurity.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserAuthRepository userAuthRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserAuthRepository userAuthRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userAuthRepository = userAuthRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userAuthRepository.existsByUsername(registerDto.username())) {
            return ResponseEntity.ok("Username already exists.");
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(registerDto.username());
        userAuth.setPassword(registerDto.password());

        Role role = roleRepository.findByRoleName("User").get();
        userAuth.setRoleList(List.of(role));

        userAuthRepository.save(userAuth);
        return ResponseEntity.ok("User registered successfully.");
    }

}

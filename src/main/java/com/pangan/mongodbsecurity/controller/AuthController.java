package com.pangan.mongodbsecurity.controller;

import com.pangan.mongodbsecurity.dto.AuthResponseDto;
import com.pangan.mongodbsecurity.dto.LoginDto;
import com.pangan.mongodbsecurity.dto.RegisterDto;
import com.pangan.mongodbsecurity.jwt.JwtTokenGenerator;
import com.pangan.mongodbsecurity.model.Role;
import com.pangan.mongodbsecurity.model.UserAuth;
import com.pangan.mongodbsecurity.repository.RoleRepository;
import com.pangan.mongodbsecurity.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserAuthRepository userAuthRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenGenerator jwtTokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userAuthRepository = userAuthRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userAuthRepository.existsByUsername(registerDto.username())) {
            return ResponseEntity.ofNullable("Username already exists.");
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(registerDto.username());
        userAuth.setPassword(passwordEncoder.encode(registerDto.password()));

        Role role = roleRepository.findByRoleName("User").get();
        userAuth.setRoleList(List.of(role));

        userAuthRepository.save(userAuth);
        return ResponseEntity.ok("User registered success.");
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenGenerator.generateToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto.AuthResponseDtoBuilder().setAccessToken(token).build();
        return ResponseEntity.ok(authResponseDto);
    }
}

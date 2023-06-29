package com.pangan.mongodbsecurity.security;

import com.pangan.mongodbsecurity.model.Role;
import com.pangan.mongodbsecurity.model.UserAuth;
import com.pangan.mongodbsecurity.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Autowired
    public CustomUserDetailsService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found."));
        List<GrantedAuthority> grantedAuthorityList = grantedAuthorityList(userAuth.getRoleList());
        return new User(userAuth.getUsername(), userAuth.getPassword(), grantedAuthorityList);
    }

    private List<GrantedAuthority> grantedAuthorityList(List<Role> roleList) {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

}

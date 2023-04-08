package com.nv.sberschool.library.service.userdetails;

import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${spring.security.user.name}")
    private String adminUserName;
    @Value("${spring.security.user.password}")
    private String adminPassword;
    @Value("${spring.security.user.roles}")
    private String adminRole;

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUserName)) {
            return new CustomUserDetails(
                    null,
                    username,
                    adminPassword,
                    List.of(new SimpleGrantedAuthority("ROLE_" + adminRole))
            );
        } else {
            User user = repository.findUserByLogin(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole().getTitle())
            );
            return new CustomUserDetails(user.getId().intValue(), username, user.getPassword(), authorities);
        }
    }
}


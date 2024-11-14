package com.cyphersoft.osahaneat.security;

import com.cyphersoft.osahaneat.entity.Users;
import com.cyphersoft.osahaneat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Kiểm tra:" + username);
        Users users = userRepository.findByUserName(username);

        if (users == null) {
            throw new UsernameNotFoundException("User can't be found");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority( users.getRoles().getRoleName()));

        return new User(username, users.getPassword(), authorities);
    }
}
package com.api.book.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.api.book.dao.UserRepository;
import com.api.book.entities.User;

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Fetching user from database and returning user details
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("Could not find user with email: " + username);
        }
        CustomUserDetail customUserDetails = new CustomUserDetail(user);

        return customUserDetails;
    }

}

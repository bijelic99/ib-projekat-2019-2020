package ib.project.service;

import ib.project.model.User;
import ib.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.getByUsername(s);
        if(!optionalUser.isPresent()) throw  new UsernameNotFoundException("User not found");
        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Arrays.asList(user.getAuthority()));

    }
}

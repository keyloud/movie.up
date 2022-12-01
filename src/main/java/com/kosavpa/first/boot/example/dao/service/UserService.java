package com.kosavpa.first.boot.example.dao.service;


import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException(String.format("User with ID = %s, not found!")));
    }

    @Transactional(readOnly = true)
    public List<UserEntity> allUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }

    @Transactional
    public boolean saveUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            return false;

        user.setRole("ROLE_USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);

            return true;
        }

        return false;
    }
}
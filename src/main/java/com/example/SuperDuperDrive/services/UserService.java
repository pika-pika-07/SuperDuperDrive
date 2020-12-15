package com.example.SuperDuperDrive.services;

import com.example.SuperDuperDrive.mapper.UserMapper;
import com.example.SuperDuperDrive.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUserNameTaken(String username) {
        return userMapper.getUser(username) != null;
    }

    public String Encoder(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public Integer getCurrentLoggedInUserId(Authentication auth) {
        String username = auth.getName();
        User user = getUser(username);
        return user.getUserid();
    }

    public User getUser(String username) {

        return userMapper.getUser(username);
    }

    public int signupUser(User user) {
        String encodedSalt = Encoder();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setSalt(encodedSalt);
        user.setPassword(hashedPassword);
        return userMapper.insert(user);
    }

}

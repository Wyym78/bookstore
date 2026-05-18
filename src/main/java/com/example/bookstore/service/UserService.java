package com.example.bookstore.service;

import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.PasswordUpdateDTO;
import com.example.bookstore.dto.RegisterDTO;
import com.example.bookstore.vo.UserVO;

public interface UserService {

    void register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);

    UserVO getUserInfo(Long userId);

    void updatePassword(Long userId, PasswordUpdateDTO passwordUpdateDTO);

    void updateProfile(Long userId, RegisterDTO registerDTO);
}
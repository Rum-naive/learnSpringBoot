package com.example.demo.sevice;

import com.example.demo.generator.User;
import com.example.demo.model.UserVO;

import java.util.List;

public interface UserService {

     UserVO saveUser(UserVO user);

     void deleteUser(Long id);

     void updateUser(UserVO user);

     UserVO getUser(Long id);

     List<User> getAll();
}
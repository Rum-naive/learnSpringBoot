package com.example.demo.sevice;

import com.example.demo.generator.User;
import com.example.demo.model.FileVO;
import com.example.demo.model.UserVO;

import java.util.List;

public interface UserService {

     UserVO saveUser(UserVO user);

     void deleteUser(Long id);

     void updateUser(UserVO user);

     UserVO getUser(Long id);

     List<User> getAll();

     UserVO Login(Long id,String Pwd);

     List<User> getUserLike(String name,Long id);

     List<User> getEmps(Long id);

     List<User> getUserLikeAll(String name);
}
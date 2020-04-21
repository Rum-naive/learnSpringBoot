package com.example.demo.model;

import com.example.demo.generator.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Long userType;           //用户类型 管理员为0 领导为1 员工为2
    private Long userId;             //用户id 用于登录和主键
    private String userName;        //用户姓名
    private String userPwd;         //用户密码
    private Long managerId;          //用户领导ID
    private Date loginTime;         //登录时间
    private List<User> employees;   //管理的员工
    private String token; //token
}

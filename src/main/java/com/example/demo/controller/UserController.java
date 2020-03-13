package com.example.demo.controller;

import com.example.demo.model.AjaxResponse;
import com.example.demo.model.UserVO;
import com.example.demo.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequestMapping("/rest")
public class UserController {

    @Resource(name = "userServiceImpl")
    UserService userService;
 
    //增加一篇user ，使用POST方法
    @RequestMapping(value = "/user", method = POST, produces = "application/json")
    public AjaxResponse saveUser(@RequestBody UserVO userVO) {

        userService.saveUser(userVO);

        return  AjaxResponse.success(userVO);
    }
 
    
    //删除一篇user，使用DELETE方法，参数是id
    @RequestMapping(value = "/user/{id}", method = DELETE, produces = "application/json")
    public AjaxResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return AjaxResponse.success(id);
    }
 
     //更新一篇user，使用PUT方法，以id为主键进行更新
    @RequestMapping(value = "/user/{id}", method = PUT, produces = "application/json")
    public AjaxResponse updateUser(@PathVariable Long id, @RequestBody UserVO userVO) {
        userVO.setUserId(id);

        userService.updateUser(userVO);

        return AjaxResponse.success(userVO);
    }
 
    //获取一个user，使用GET方法
    @RequestMapping(value = "/user/{id}", method = GET, produces = "application/json")
    public AjaxResponse getUser(@PathVariable Long id) {

        return AjaxResponse.success(userService.getUser(id));
    }

    //获取所有user，使用GET方法
    @RequestMapping(value = "/user", method = GET, produces = "application/json")
    public AjaxResponse getAll() {

        return AjaxResponse.success(userService.getAll());
    }
}
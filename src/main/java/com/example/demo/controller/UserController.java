package com.example.demo.controller;

import com.example.demo.config.exception.AjaxResponse;
import com.example.demo.generator.User;
import com.example.demo.model.FileVO;
import com.example.demo.model.UserVO;
import com.example.demo.sevice.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequestMapping("/rest")
public class UserController {

    @Resource(name = "userServiceImpl")
    UserService userService;
 
    //增加一个user ，使用POST方法
    @RequestMapping(value = "/user", method = POST, produces = "application/json")
    public AjaxResponse saveUser(@RequestBody UserVO userVO) {

        userService.saveUser(userVO);

        return  AjaxResponse.success(userVO);
    }
 
    
    //删除一个user，使用DELETE方法，参数是id
    @RequestMapping(value = "/user/{id}", method = DELETE, produces = "application/json")
    public AjaxResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return AjaxResponse.success(id);
    }
 
     //更新一个user，使用PUT方法，以id为主键进行更新
    @RequestMapping(value = "/user/{id}", method = PUT, produces = "application/json")
    public AjaxResponse updateUser(@PathVariable Long id, @RequestBody UserVO userVO) {
        userVO.setUserId(id);
        userService.updateUser(userVO);

        return AjaxResponse.success();
    }
 
    //获取一个user，使用GET方法
    @RequestMapping(value = "/user/{id}", method = GET, produces = "application/json")
    public AjaxResponse getUser(@PathVariable Long id) {
        return AjaxResponse.success(userService.getUser(id));
    }

    //获取所有user，使用GET方法
    @RequestMapping(value = "/user", method = GET, produces = "application/json")
    public AjaxResponse getAll(int pageNum,int pageSize) {
        List<User> users1 = userService.getAll();
        int total = (int) new PageInfo<>(users1).getTotal();
        PageHelper.startPage(pageNum,pageSize);
        List<User> users2 = userService.getAll();
        PageInfo<User> pageInfo = new PageInfo<User>(users2);
        pageInfo.setTotal(total);
        return AjaxResponse.success(pageInfo);
    }

    //用户登录
    @RequestMapping(value = "/user/login", method = GET, produces = "application/json")
    public AjaxResponse Login(Long id,String password) {
        UserVO user = userService.Login(id,password);
        return AjaxResponse.success(user);
    }

    //用户退出
    @RequestMapping(value = "/user/logout", method = POST, produces = "application/json")
    public AjaxResponse logout() {
        return AjaxResponse.success();
    }

    //获取用户手下
    @RequestMapping(value = "/user/emp/{id}", method = GET, produces = "application/json")
    public AjaxResponse getEmps(@PathVariable Long id,int pageNum,int pageSize) {
        List<User> users1 = userService.getEmps(id);
        int total = (int) new PageInfo<>(users1).getTotal();
        PageHelper.startPage(pageNum,pageSize);
        List<User> users2 = userService.getEmps(id);
        PageInfo<User> pageInfo = new PageInfo<User>(users2);
        pageInfo.setTotal(total);
        return AjaxResponse.success(pageInfo);
    }

    //模糊查询userName，使用GET方法
    @RequestMapping(value = "/user/query/{id}/{name}", method = GET, produces = "application/json")
    public AjaxResponse getUserLike(@PathVariable Long id,@PathVariable String name,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list =userService.getUserLike(name,id);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return AjaxResponse.success(pageInfo);
    }

    //模糊查询userName，使用GET方法
    @RequestMapping(value = "/user/query/{name}", method = GET, produces = "application/json")
    public AjaxResponse getUserLikeAll(@PathVariable String name,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list =userService.getUserLikeAll(name);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return AjaxResponse.success(pageInfo);
    }
}
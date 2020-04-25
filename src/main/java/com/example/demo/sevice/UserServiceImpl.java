package com.example.demo.sevice;

import com.example.demo.config.exception.CustomException;
import com.example.demo.config.exception.CustomExceptionType;
import com.example.demo.generator.*;
import com.example.demo.model.FileVO;
import com.example.demo.model.UserVO;
import com.example.demo.utils.DozerUtils;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    protected Mapper dozerMapper;

    @Resource
    private UserMapper userMapper;


    //新增
    @Override
    public UserVO saveUser(UserVO user) {
        User userPO = dozerMapper.map(user,User.class);
        userMapper.insert(userPO);
        return null;
    }

    //删除
    @Override
    public void deleteUser(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    //更新
    @Override
    public void updateUser(UserVO user) {
        User userPO = dozerMapper.map(user,User.class);
        userMapper.updateByPrimaryKeySelective(userPO);
    }

    //查询
    @Override
    public UserVO getUser(Long id) {
        UserVO userVO = dozerMapper.map(userMapper.selectByPrimaryKey(id),UserVO.class);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andManagerIdEqualTo(userVO.getUserId());
        List<User> users = userMapper.selectByExample(userExample);
        userVO.setEmployees(users);
        return userVO;
    }

    //查询所有
    @Override
    public List<User> getAll() {
        List<User> users = userMapper.selectByExample(null);
        return users;
    }

    //登录
    @Override
    public UserVO Login(Long id, String Pwd) throws CustomException {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(id).andUserPwdEqualTo(Pwd);
        User user = new User();
        try {
            user = userMapper.selectByExample(userExample).get(0);
        }catch (IndexOutOfBoundsException e){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,"您输入的用户名或者密码错误，请确认后重新输入！");
        }
        user.setLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        String token = UUID.randomUUID()+"";
        UserVO userVO = dozerMapper.map(user, UserVO.class);
        userVO.setToken(token);
        return userVO;
    }

    @Override
    public List<User> getUserLike(String name,Long id) {
        String query_name = "%" + name + "%";
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameLike(query_name).andManagerIdEqualTo(id);
        List<User> query_like = userMapper.selectByExample(userExample);
        return query_like;
    }

    @Override
    public List<User> getEmps(Long id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andManagerIdEqualTo(id);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public List<User> getUserLikeAll(String name) {
        String query_name = "%" + name + "%";
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameLike(query_name);
        List<User> query_like = userMapper.selectByExample(userExample);
        return query_like;
    }
}

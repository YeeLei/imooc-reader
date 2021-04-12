package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.User;
import com.imooc.reader.mapper.UserMapper;
import com.imooc.reader.service.UserService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    public User checkLogin(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
        if (user == null) {
            throw new BussinessException("M02","用户名不存在");
        }
        String userPassword = user.getPassword();
        String md5 = MD5Util.md5Digest(password, user.getSalt());
        if (!userPassword.equals(md5)) {
            throw new BussinessException("M03", "密码有误");
        }
        return user;
    }


}

package com.imooc.reader.service;

import com.imooc.reader.entity.User;

public interface UserService {
    /**
     * 登录检查
     *
     * @param username 用户名
     * @param password 密码
     * @return 会员对象
     */
    public User checkLogin(String username, String password);
}

package com.demo.fileoperation.service.impl;

import com.demo.fileoperation.domain.User;
import com.demo.fileoperation.mapper.UserMapper;
import com.demo.fileoperation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) {
        User u = null;
        try {
            u = userMapper.selectOne(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public boolean register(User user) {
        int flag = userMapper.insertSelective(user);
        if (flag>0) return true;
        return false;
    }
}

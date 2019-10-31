package com.demo.fileoperation.service;

import com.demo.fileoperation.domain.User;

public interface UserService {
    User login(User user);

    boolean register(User user);
}

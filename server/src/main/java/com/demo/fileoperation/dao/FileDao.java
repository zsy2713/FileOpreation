package com.demo.fileoperation.dao;

import com.demo.fileoperation.domain.Info;

import java.util.List;

public interface FileDao {
    int insertInfo(Info info);

    List<Info> findAll();

    Info getInfo(String uuid);
}

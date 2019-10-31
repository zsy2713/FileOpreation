package com.demo.fileoperation.service;

import com.demo.fileoperation.domain.Info;

import java.util.List;

public interface FileService {
    boolean save(Info info);

    List<Info> findAll();

    Info getInfo(String uuid);
}

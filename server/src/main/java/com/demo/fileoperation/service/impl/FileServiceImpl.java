package com.demo.fileoperation.service.impl;

import com.demo.fileoperation.dao.FileDao;
import com.demo.fileoperation.dao.impl.FileDaoImpl;
import com.demo.fileoperation.domain.Info;
import com.demo.fileoperation.service.FileService;

import java.util.List;

public class FileServiceImpl implements FileService {

    FileDao fileDao = new FileDaoImpl();
    public boolean save(Info info) {
        int flag = fileDao.insertInfo(info);
        if (flag>0) return true;
        return false;
    }

    public List<Info> findAll() {
        return fileDao.findAll();
    }

    public Info getInfo(String uuid) {
        return fileDao.getInfo(uuid);
    }
}

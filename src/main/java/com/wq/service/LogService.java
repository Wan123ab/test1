package com.wq.service;

import com.wq.dao.LogDaoImpl;
import com.wq.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogDaoImpl logDao;

    public void saveLog(Log log) {


        logDao.add(log);
    }

}

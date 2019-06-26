package com.wq.log;

import com.wq.dao.UUID;
import com.wq.entity.Log;
import com.wq.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LogAspect {

    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.wq.log.WriteLog)")
    public void log() {
    }

    ;

    @After("log()")
    public void saveLog(JoinPoint jp) {

        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();

        WriteLog writeLog = method.getAnnotation(WriteLog.class);

        Log log = Log.builder().id(UUID.getId()).operDesc(writeLog.operDesc()).operModule(writeLog.operModule())
                .operType(writeLog.operType().toString()).build();

        logService.saveLog(log);

    }


}

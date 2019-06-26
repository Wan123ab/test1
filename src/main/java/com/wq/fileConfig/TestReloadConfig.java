package com.wq.fileConfig;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试disconf修改文件后reload以及调用回调函数
 */
@Component
@DisconfFile(filename = "TestReload.properties")
@DisconfUpdateService(classes = {TestReloadConfig.class})
public class TestReloadConfig implements IDisconfUpdate {

    private static Logger logger = LoggerFactory.getLogger(TestReloadConfig.class);

    private String name;

    private int age;

    private String address;

    @DisconfFileItem(name = "name", associateField = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DisconfFileItem(name = "age", associateField = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @DisconfFileItem(name = "address", associateField = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 每次更新分布式配置都会调reload方法
     *
     * @throws Exception
     */
    @Override
    public void reload() throws Exception {

        logger.info("检测到更新分布式配置，新的配置为：{},{},{}", name, age, address);
    }
}

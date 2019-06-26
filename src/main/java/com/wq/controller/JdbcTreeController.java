package com.wq.controller;

import com.wq.dao.JdbcDaoImpl;
import com.wq.entity.Tree;
import com.wq.log.OperType;
import com.wq.log.WriteLog;
import com.wq.redis.RedisUtils;
import com.wq.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jdbc")
@Api(value = "jdbc登录页面", description = "关于jdbc的操作")
public class JdbcTreeController extends BaseController{

    /**
     * logger和log都可以正常使用
     */
    private Logger logger = LoggerFactory.getLogger(JdbcTreeController.class);

    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    @Autowired
    private JdbcDaoImpl jdbcDao;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ApiOperation(value = "登录jdbc首页", notes = "登录到首页", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, hidden = true)
    @Cacheable
    public String login() throws Exception {

        getBasePath();

        System.out.println("=======================================");
        getReleaseAddr();

        //jdbcDao.index();

        RedisUtils.set("tree1", new Tree(1, -1, "浙江省"), -1);

        Tree tree = RedisUtils.get("tree1", Tree.class);
        logger.info("读取缓存返回={}", tree);

        return "Welcome To JDBC Demo";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @WriteLog(operDesc = "创建树节点", operModule = "树节点", operType = OperType.CREATE)
    public Tree create(@RequestBody Tree tree) {

        jdbcDao.add(tree);
        Tree result = jdbcDao.queryById(tree.getId());

        //sl4j打印日志可以使用{}作为占位符，比log.4j更加强大
        logger.info("创建Tree{}，返回{}", "成功", JsonUtils.obj2Json(tree));

        //log.info("创建Tree，返回"+new Gson().toJson(tree));
        return result;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Integer id) {

        jdbcDao.delete(id);
        return "删除成功";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Tree tree) {

        jdbcDao.update(tree);
        return "修改成功";
    }

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Tree query(@PathVariable Integer id) {

        Tree tree = jdbcDao.queryById(id);
        return tree;
    }


    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Tree> queryAll() {

        List<Tree> trees = jdbcDao.queryAll();
        return trees;
    }


}

package com.wq.controller;

import com.wq.dao.Dao;
import com.wq.entity.Condition;
import com.wq.entity.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mongoDB")
public class MongoDBTreeController {

    @Autowired
    @Qualifier("mongoDBDaoImpl")
    private Dao treeDao;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login() {

        return "Welcome To MongoDB Demo";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Tree create(Tree tree) {

        treeDao.add(tree);
        Tree result = treeDao.queryById(tree.getId(), Tree.class);
        return result;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Integer id) {

        treeDao.delete(id, Tree.class);
        return "删除成功";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Tree tree) {

        treeDao.saveOrUpdate(tree);
        return "修改成功";
    }

    @RequestMapping(value = "/query/{id}", method = RequestMethod.GET)
    public Tree query(@PathVariable Integer id) {

        Tree tree = treeDao.queryById(id, Tree.class);
        return tree;
    }

    @RequestMapping(value = "/queryByParam", method = RequestMethod.GET)
    public List<Tree> queryByName(Integer pid, String name) {

        Condition condition = new Condition();
        condition.eq("pid", pid);
        condition.eq("name", name);
        List<Tree> trees = treeDao.queryByCondition(condition, Tree.class);
        return trees;
    }

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Tree> queryAll() {

        List<Tree> trees = treeDao.queryAll(Tree.class);
        return trees;
    }

}

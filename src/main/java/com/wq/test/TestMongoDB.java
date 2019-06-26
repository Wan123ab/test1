package com.wq.test;

import com.wq.dao.MongoDBDaoImpl;
import com.wq.entity.Condition;
import com.wq.entity.Tree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:springmvc.xml"})
public class TestMongoDB {

    @Autowired
    private MongoDBDaoImpl dao;

    @Test
    public void save() {
        Tree tree1 = new Tree(2, 1, "杭州市");
        Tree tree2 = new Tree(3, 1, "湖州市");
        Tree tree3 = new Tree(4, 1, "嘉兴市");
        Tree tree4 = new Tree(5, 1, "金华市");
        Tree tree5 = new Tree(6, 1, "台州市");
        Tree tree6 = new Tree(7, 1, "衢州市");
        List<Tree> list = new ArrayList<>();
        list.add(tree1);
        list.add(tree2);
        list.add(tree3);
        list.add(tree4);
        list.add(tree5);
        list.add(tree6);

        dao.insertBatch(list);
    }

    @Test
    public void delete() {
        dao.delete(1, Tree.class);
    }

    @Test
    public void update() {
        Tree tree = dao.queryById(1, Tree.class);
        tree.setPid(null);
        dao.saveOrUpdate(tree);
    }

    @Test
    public void queryById() {

        Tree tree = dao.queryById(1, Tree.class);
        System.out.println(tree);
    }

    @Test
    public void queryByParams() {

        List<Tree> trees = dao.queryByParam(new String[]{"pid"}, new Object[]{1}, "id desc", Tree.class);
        System.out.println(trees);
    }

    @Test
    public void queryByCondition() {

        Condition condition = new Condition();

        //in查询
        condition.in("id", 1, 2, 3, 4, 5, 6, 7);
        //模糊查询
        condition.like("name", "市");
        //排序
        condition.setOrder("id");

        //分页查询，查出4条记录
        condition.setStart(0);
        condition.setEnd(4);

        List<Tree> trees = dao.queryByCondition(condition, Tree.class);
        System.out.println(trees);


    }

    @Test
    public void startApp() {

        System.out.println("==================启动容器====================");


    }

}

package com.wq.dao;

import com.wq.entity.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcDaoImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    /**
     * 测试日志打印
     */
    public void index() {

//        logger.debug("sl4j");
//        logger.info("sl4j");
//        logger.warn("sl4j");
//        logger.error("sl4j");
//
//        log.debug("log4j");
//        log.info("log4j");
//        log.warn("log4j");
//        log.error("log4j");


    }


    //@WriteLog(operDesc = "创建树节点",operModule = "树节点",operType = OperType.CREATE)
    //@Transactional(rollbackFor = Exception.class)//若是这种配置那么必须抛出异常才能回滚，并且WriteLog也无法成功保存log
    @Transactional//若是这种配置那么即使没有手动抛出异常也会回滚，并且WriteLog也无法成功保存log
    public void add(Tree tree) {
        String sql = "insert into tree values(?,?,?,?)";
        int update = jdbcTemplate.update(sql, tree.getId(), tree.getPid(), tree.getName(), tree.getCreateDate());

        //System.out.println(1/0);//配合@Transactional

        //throw new NullPointerException();//配合@Transactional(rollbackFor = Exception.class)
    }

    public void delete(Integer id) {
        String sql = "delete from tree where id = ?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("删除" + (update == 1 ? "成功" : "失败"));
    }

    public void update(Tree tree) {
        String sql = "update tree set pid = ?,name = ? where id = ?";
        int update = jdbcTemplate.update(sql, tree.getPid(), tree.getName(), tree.getId());
        System.out.println("修改" + (update == 1 ? "成功" : "失败"));
    }

    public Tree queryById(Integer id) {
        String sql = "select * from tree where id = ?";
        Tree tree = jdbcTemplate.queryForObject(sql, new TreeRowMapper(), id);
        return tree;
    }

    public List queryAll() {
        String sql = "select * from tree";
        List<Tree> list = jdbcTemplate.query(sql, new TreeRowMapper());
        return list;
    }

    public List queryFields() {
        String sql = "select id,pid,name from tree";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    public void insertBatch(List list) {

    }

    public long countQuery(String[] params, Object[] values, Class clz) {
        String sql = "select count(*) from tree ";
        Long num = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println("共有" + num + "条记录");
        return num;
    }


    class TreeRowMapper implements RowMapper<Tree> {


        @Override
        public Tree mapRow(ResultSet rs, int i) throws SQLException {
            Tree tree = new Tree();
            tree.setId(rs.getInt("id"));
            tree.setPid(rs.getInt("pid"));
            tree.setName(rs.getString("name"));
            return tree;
        }
    }

}

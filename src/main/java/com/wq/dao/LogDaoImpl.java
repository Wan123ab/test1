package com.wq.dao;

import com.wq.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class LogDaoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Log log) {
        String sql = "insert into log values(?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, log.getId(), log.getOperModule(), log.getOperDesc(), log.getOperType(), new Date());
        System.out.println("创建" + (update == 1 ? "成功" : "失败"));
    }

    public void delete(Integer id) {
        String sql = "delete from log where id = ?";
        int update = jdbcTemplate.update(sql, id);
        System.out.println("删除" + (update == 1 ? "成功" : "失败"));
    }

    public void update(Log log) {
        String sql = "update log set operDesc = ? where id = ?";
        int update = jdbcTemplate.update(sql, log.getOperDesc(), log.getId());
        System.out.println("修改" + (update == 1 ? "成功" : "失败"));
    }

    public Log queryById(Integer id) {
        String sql = "select * from log where id = ?";
        Log log = jdbcTemplate.queryForObject(sql, new LogDaoImpl.LogRowMapper(), id);
        return log;
    }

    public List queryAll() {
        String sql = "select * from tree";
        List<Log> list = jdbcTemplate.query(sql, new LogDaoImpl.LogRowMapper());
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


    class LogRowMapper implements RowMapper<Log> {


        @Override
        public Log mapRow(ResultSet rs, int i) throws SQLException {
            Log log = new Log();
            log.setId(rs.getString("id"));
            log.setOperDesc(rs.getString("operDesc"));
            log.setOperModule(rs.getString("operModule"));
            log.setOperType(rs.getString("operType"));
            log.setCreateDate(rs.getDate("createDate"));
            return log;
        }
    }
}

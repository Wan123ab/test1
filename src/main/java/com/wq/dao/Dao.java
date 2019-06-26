package com.wq.dao;

import com.wq.entity.Condition;

import java.util.List;

/**
 * 通用Dao接口
 */
public interface Dao {

    <T> void add(T entity);

    <T> void delete(Integer id, Class<T> clz);

    <T> void saveOrUpdate(T entity);

    <T> T queryById(Integer id, Class<T> clz);

    <T> List<T> queryAll(Class<T> clz);

    <T> void insertBatch(List<T> list);

    <T> List<T> queryByParam(String[] params, Object[] values, String order, Class<T> clz);

    <T> long countQuery(String[] params, Object[] values, Class<T> clz);

    <T> List<T> queryByCondition(Condition condition, Class<T> clz);

}

package com.wq.entity;

import org.springframework.data.mongodb.core.query.Criteria;

import java.util.*;

/**
 * mongodb条件查询封装类
 */
public class Condition {

    private List<Criteria> list = new ArrayList<>();

    private String order;

    private Integer start;

    private Integer end;

    public List<Criteria> getList() {
        return list;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * 等于
     *
     * @param key
     * @param value
     */
    public void eq(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).is(value));

    }

    /**
     * 不等于
     *
     * @param key
     * @param value
     */
    public void ne(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).ne(value));

    }

    /**
     * 小于
     *
     * @param key
     * @param value
     * @return
     */
    public void lt(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).lt(value));

    }

    /**
     * 小于等于
     *
     * @param key
     * @param value
     * @return
     */
    public void lte(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).lte(value));

    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public void gt(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).gt(value));

    }

    /**
     * 大于等于
     *
     * @param key
     * @param value
     * @return
     */
    public void gte(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).gte(value));

    }

    /**
     * in查询
     *
     * @param key
     * @param c
     * @return
     */
    public void in(Object key, Collection<?> c) {

        list.add(Criteria.where(String.valueOf(key)).in(c));

    }

    /**
     * in查询
     *
     * @param key
     * @param value
     * @return
     */
    public void in(Object key, Object... value) {

        list.add(Criteria.where(String.valueOf(key)).in(value));

    }

    /**
     * not in查询
     *
     * @param key
     * @param c
     * @return
     */
    public void notIn(Object key, Collection<?> c) {

        list.add(Criteria.where(String.valueOf(key)).nin(c));

    }

    /**
     * 模糊查询
     *
     * @param key
     * @param value
     * @return
     */
    public void like(Object key, Object value) {

        list.add(Criteria.where(String.valueOf(key)).regex(".*?\\" + String.valueOf(value) + ".*"));

    }

    /**
     * TODO
     * or查询
     *
     * @param criteria
     */
    public void or(Criteria... criteria) {

        list.add(new Criteria().orOperator(criteria));

    }


}

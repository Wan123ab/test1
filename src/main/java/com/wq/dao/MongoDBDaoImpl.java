package com.wq.dao;

import com.mongodb.WriteResult;
import com.wq.entity.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用dao实现类
 */
@Repository
public class MongoDBDaoImpl implements Dao {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public <T> void add(T entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public <T> void delete(Integer id, Class<T> clz) {
        T entity = queryById(id, clz);
        final WriteResult result = mongoTemplate.remove(entity);
    }

    @Override
    public <T> void saveOrUpdate(T entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public <T> T queryById(Integer id, Class<T> clz) {
        T entity = mongoTemplate.findById(id, clz);
        return entity;
    }

    @Override
    public <T> List<T> queryAll(Class<T> clz) {
        List<T> list = mongoTemplate.findAll(clz);
        return list;
    }

    @Override
    public <T> void insertBatch(List<T> list) {
        mongoTemplate.insert(list);
    }

    /**
     * 多条件where查询，并且进行排序
     *
     * @param params
     * @param values
     * @param order
     * @return
     */
    @Override
    public <T> List<T> queryByParam(String[] params, Object[] values, String order, Class<T> clz) {

        Query query = createQuery(params, values, order);
        List<T> list = mongoTemplate.find(query, clz);

        return list;

    }

    /**
     * 查询符合条件的记录个数
     *
     * @param params
     * @param values
     * @return
     */
    @Override
    public <T> long countQuery(String[] params, Object[] values, Class<T> clz) {

        Query query = createQuery(params, values, null);
        Long count = mongoTemplate.count(query, clz);

        return count;

    }


    /**
     * 创建带有where条件（只支持=）和排序的Query对象
     *
     * @param params 参数数组
     * @param values 参数值数组
     * @param order  排序
     * @return
     */
    protected Query createQuery(String[] params, Object[] values, String order) {
        Query query = new Query();

        //添加where条件
        if (params != null && params.length > 0 && values != null && values.length > 0 && params.length == values.length) {
            for (int i = 0; i < params.length; i++) {
                query.addCriteria(Criteria.where(params[i]).is(values[i]));
            }
        }

        //排序
        List<Sort.Order> orders = parseOrder(order);
        if (orders != null && orders.size() > 0) {
            query.with(new Sort(orders));
        }

        return query;

    }

    /**
     * @param order 排序字段，如[id]、[id asc]、[id asc,name desc]
     * @return
     */
    protected List<Sort.Order> parseOrder(String order) {

        List<Sort.Order> list = null;
        if (!StringUtils.isEmpty(order)) {
            list = new ArrayList<>();

            String[] fields = order.split(",");
            Sort.Order order1 = null;

            for (String field : fields) {
                if (StringUtils.isEmpty(field)) {
                    continue;
                }

                if (field.split(" ").length == 1) {//Asc
                    order1 = new Sort.Order(Sort.Direction.ASC, field.split(" ")[0]);

                } else if (field.split(" ").length == 2) {
                    order1 = new Sort.Order("desc".equalsIgnoreCase(field.split(" ")[1]) ? Sort.Direction.DESC : Sort.Direction.ASC, field.split(" ")[0]);
                } else {
                    throw new RuntimeException("排序字段解析出错，请检查！");
                }
                list.add(order1);
            }
        }
        return list;
    }

    /**
     * condition组装查询
     *
     * @param condition
     * @return
     */
    @Override
    public <T> List<T> queryByCondition(Condition condition, Class<T> clz) {

        List<T> list = null;
        Query query = null;
        if (condition != null && !CollectionUtils.isEmpty(condition.getList())) {
            list = new ArrayList<>();
            query = new Query();

            //where查询条件
            for (Criteria criteria : condition.getList()) {
                query.addCriteria(criteria);

            }

            //order排序
            List<Sort.Order> orders = parseOrder(condition.getOrder());
            if (orders != null && orders.size() > 0) {
                query.with(new Sort(orders));
            }

            //设置分页参数
            if (condition.getStart() != null && condition.getEnd() != null) {

                query.limit(condition.getEnd() - condition.getStart()).skip(condition.getStart());
            }

            list = mongoTemplate.find(query, clz);

        }

        return list;
    }


}

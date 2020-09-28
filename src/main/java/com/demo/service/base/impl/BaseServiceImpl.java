package com.demo.service.base.impl;

import com.demo.dao.base.BaseDao;
import com.demo.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //利用spring4.0新特性，泛型注入
    //必须用@Autowired而不是@Resource
    @Autowired
    private BaseDao<T> baseDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return baseDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(T record) {
        return baseDao.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return baseDao.insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        return baseDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return baseDao.updateByPrimaryKey(record);
    }
}

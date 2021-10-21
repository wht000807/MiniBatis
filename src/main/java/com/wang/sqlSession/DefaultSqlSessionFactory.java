package com.wang.sqlSession;

import com.wang.pojo.Configuration;

/**
 * @Description:
 * @Author: wht
 * @Date: 2021/10/22/0:14
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}

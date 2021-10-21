package com.wang.sqlSession;

import com.wang.pojo.Configuration;
import com.wang.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: wht
 * @Date: 2021/10/22/1:15
 */
public interface Executor {

    public <E>List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException;

}

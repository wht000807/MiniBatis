package com.wang.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description:会话对象接口
 * @Author: wht
 * @Date: 2021/10/22/0:50
 */
public interface SqlSession {

    //查询所有
    public <E>List<E> selectList(String statementId,Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;

    //查询单个
    public <T> T selectOne(String statementId,Object... params) throws SQLException, IntrospectionException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException;




}

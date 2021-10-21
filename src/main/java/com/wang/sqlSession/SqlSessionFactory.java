package com.wang.sqlSession;

/**
 * @Description:会话对象工厂
 * @Author: wht
 * @Date: 2021/10/21/19:58
 */
public interface SqlSessionFactory {

    public SqlSession openSession();

}

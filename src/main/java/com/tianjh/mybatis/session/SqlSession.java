package com.tianjh.mybatis.session;

import java.io.Closeable;

/**
 * 这是MyBatis主要的一个类，用来执行SQL，获取映射器，管理事务
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public interface SqlSession extends Closeable {

    /**
     * Retrieve a single row mapped from the statement key
     * 查询一条记录
     * @param <T> the returned object type
     * @param statement
     * @return Mapped object
     */
    <T> T selectOne(String statement);
}

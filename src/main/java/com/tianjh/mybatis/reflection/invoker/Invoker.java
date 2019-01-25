package com.tianjh.mybatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/9
 * @since: 1 description:
 */
public interface Invoker {

    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

    Class<?> getType();
}

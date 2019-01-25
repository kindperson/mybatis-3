package com.tianjh.mybatis.reflection;


/**
 * @author: wb-tjh438466
 * @date: 2019/1/9
 * @since: 1 description:
 */
public interface ReflectorFactory {

    Reflector findForClass(Class<?> type);
}

package com.tianjh.mybatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/24
 * @since: 1 description:
 */
public class MethodInvoker implements Invoker{

    private Class<?> type;
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;

        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}

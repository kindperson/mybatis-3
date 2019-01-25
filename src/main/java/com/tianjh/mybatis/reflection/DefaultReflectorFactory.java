package com.tianjh.mybatis.reflection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 反射工厂类的实现
 * @author: wb-tjh438466
 * @date: 2019/1/9
 * @since: 1 description:
 */
public class DefaultReflectorFactory  implements ReflectorFactory{
    /**
     * 是否启用缓存，默认是true
     */
    private boolean classCacheEnabled = true;
    /**
     * 缓存反射类，key：类class，value：根据类class创建的反射类
     */
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<Class<?>, Reflector>();

    /**
     * 创建反射类并放入到缓存中
     * @param type
     * @return
     */
    @Override
    public Reflector findForClass(Class<?> type) {
        if (classCacheEnabled) {
            // synchronized (type) removed see issue #461
            Reflector cached = reflectorMap.get(type);
            if (cached == null) {
                cached = new Reflector(type);
                reflectorMap.put(type, cached);
            }
            return cached;
        } else {
            return new Reflector(type);
        }
    }
}

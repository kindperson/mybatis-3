package com.tianjh.mybatis.io;

/**
 * 类加载器的封装
 * https://blog.csdn.net/javaee_ssh/article/details/72724110   类加载器的详细介绍
 * @author: wb-tjh438466
 * @date: 2018/12/6
 * @since: 1 description:
 */
public class ClassLoaderWrapper {
    /**
     * 默认类加载器，指定的类加载器
     */
    private ClassLoader defaultClassloader;
    /**
     * 系统类加载器
     */
    private ClassLoader systemClassloader;

    ClassLoaderWrapper() {
        systemClassloader = ClassLoader.getSystemClassLoader();
    }

    public ClassLoader getDefaultClassloader() {
        return defaultClassloader;
    }

    public void setDefaultClassloader(ClassLoader defaultClassloader) {
        this.defaultClassloader = defaultClassloader;
    }

    public ClassLoader getSystemClassloader() {
        return systemClassloader;
    }

    public void setSystemClassloader(ClassLoader systemClassloader) {
        this.systemClassloader = systemClassloader;
    }
}

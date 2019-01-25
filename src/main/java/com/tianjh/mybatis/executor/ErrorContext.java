package com.tianjh.mybatis.executor;

/**
 * 日志信息类，这里用单例模式实例化日志信息类
 * 每个返回方法都是返回日志类对象，可以直接用“.”方法访问
 * @author: wb-tjh438466
 * @date: 2019/1/8
 * @since: 1 description:
 */
public class ErrorContext {
    private static final ThreadLocal<ErrorContext> LOCAL = new ThreadLocal<>();
    /**
     * 配置信息日志
     */
    private String resource;

    /**
     * 用单例模式创建错误信息类
     * @return
     */
    public static ErrorContext instance() {
        ErrorContext context = LOCAL.get();
        if (context == null) {
            context = new ErrorContext();
            LOCAL.set(context);
        }
        return context;
    }

    public ErrorContext resource(String resource) {
        this.resource = resource;
        return this;
    }
}

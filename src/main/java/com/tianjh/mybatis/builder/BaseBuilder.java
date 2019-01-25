package com.tianjh.mybatis.builder;

import com.tianjh.mybatis.session.Configuration;
import com.tianjh.mybatis.type.TypeAliasRegistry;
import com.tianjh.mybatis.type.TypeHandlerRegistry;

import java.io.Reader;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public abstract class BaseBuilder {

    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;
    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }
}

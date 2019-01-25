package com.tianjh.mybatis.session;

import com.tianjh.mybatis.type.TypeAliasRegistry;
import com.tianjh.mybatis.type.TypeHandlerRegistry;

import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public class Configuration {

    private TypeAliasRegistry typeAliasRegistry=new TypeAliasRegistry();

    private TypeHandlerRegistry typeHandlerRegistry=new TypeHandlerRegistry();

    private Properties variables=new Properties();;

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }


    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public Properties getVariables() {
        return variables;
    }
}

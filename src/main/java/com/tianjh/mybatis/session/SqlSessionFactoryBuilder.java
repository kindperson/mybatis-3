package com.tianjh.mybatis.session;

import com.tianjh.mybatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(Reader reader) {
        return build(reader, null, null);
    }

    /**
     * 创建sqlSessionFactory
     * @param reader
     * @param environment
     * @param properties
     * @return
     */
    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
          // return build(parser.parse());
            return null;
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }
}

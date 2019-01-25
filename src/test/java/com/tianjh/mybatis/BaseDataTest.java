package com.tianjh.mybatis;


import com.tianjh.mybatis.datasource.unpooled.UnpooledDataSource;
import com.tianjh.mybatis.io.Resources;
import com.tianjh.mybatis.jdbc.ScriptRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public abstract class BaseDataTest {

    public static final String BLOG_PROPERTIES = "org/apache/ibatis/databases/blog/blog-derby.properties";
    public static final String BLOG_DDL = "org/apache/ibatis/databases/blog/blog-derby-schema.sql";
    public static final String BLOG_DATA = "org/apache/ibatis/databases/blog/blog-derby-dataload.sql";

    /**
     * 根据配置文件连接sql
     * @param resource
     * @return
     * @throws IOException
     */
    public static UnpooledDataSource createUnpooledDataSource(String resource) throws IOException {
        Properties props = Resources.getResourceAsProperties(resource);
        UnpooledDataSource ds = new UnpooledDataSource();
        ds.setDriver(props.getProperty("driver"));
        ds.setUrl(props.getProperty("url"));
        ds.setUsername(props.getProperty("username"));
        ds.setPassword(props.getProperty("password"));
        return ds;
    }

    /**
     * 创建blog数据库连接
     * @return
     * @throws IOException
     * @throws SQLException
     */
    public static DataSource createBlogDataSource() throws IOException, SQLException {
        //连接数据库
        DataSource ds = createUnpooledDataSource(BLOG_PROPERTIES);
        //执行ddlsql
        runScript(ds, BLOG_DDL);
        //执行datasql
        runScript(ds, BLOG_DATA);
        return ds;
    }

    public static void runScript(DataSource ds, String resource) throws IOException, SQLException {
        Connection connection = ds.getConnection();
        try {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.setLogWriter(new PrintWriter(System.out));
            runner.setErrorLogWriter(new PrintWriter(System.out));
            runScript(runner, resource);
        } finally {
            connection.close();
        }
    }

    public static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {
        Reader reader = Resources.getResourceAsReader(resource);
        try {
            runner.runScript(reader);
        } finally {
            reader.close();
        }
    }
}

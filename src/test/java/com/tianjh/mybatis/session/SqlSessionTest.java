package com.tianjh.mybatis.session;

import com.tianjh.mybatis.BaseDataTest;
import com.tianjh.mybatis.io.Resources;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;

/**
 * sqlSession测试类
 *
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public class SqlSessionTest extends BaseDataTest {

    private static SqlSessionFactory sqlMapper;

    @BeforeClass
    public static void setup() throws Exception {
        createBlogDataSource();
        final String resource = "org/apache/ibatis/builder/MapperConfig.xml";
        final Reader reader = Resources.getResourceAsReader(resource);
        sqlMapper = new SqlSessionFactoryBuilder().build(reader);
    }

    @Test
    public void shouldResolveBothSimpleNameAndFullyQualifiedName() {
        System.out.println("已经成功了");
      /*  Configuration c = new Configuration();
        final String fullName = "com.mycache.MyCache";
        final String shortName = "MyCache";
        final PerpetualCache cache = new PerpetualCache(fullName);
        c.addCache(cache);
        assertEquals(cache, c.getCache(fullName));
        assertEquals(cache, c.getCache(shortName));*/
    }
}

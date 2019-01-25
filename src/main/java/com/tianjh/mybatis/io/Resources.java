package com.tianjh.mybatis.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/6
 * @since: 1 description:
 */
public class Resources {
    private static ClassLoaderWrapper classLoaderWrapper=new ClassLoaderWrapper();
   public static Properties getResourceAsProperties(String resource){
       InputStream in = classLoaderWrapper.getSystemClassloader().getResourceAsStream(resource);
       Properties properties=new Properties();
       try {
           properties.load(in);
           return properties;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

    public static Reader getResourceAsReader(String resource) {
        InputStream inputStream=classLoaderWrapper.getSystemClassloader().getResourceAsStream(resource);
        return  new InputStreamReader(inputStream);
    }
}

package com.tianjh.mybatis.session;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/18
 * @since: 1 description:
 */
public class Test {
   public static  void main(String[] args){
       String trimmedLine="--//@DELIMITER /";
       final String cleanedString = trimmedLine.substring(2).trim().replaceFirst("//", "");
       if(cleanedString.toUpperCase().startsWith("@DELIMITER")) {
           String delimiter = cleanedString.substring(11, 12);
           System.out.println(delimiter);
       }

       List<Student> students=new ArrayList<>();
       Student st=new Student();
       st.setAge(18);
       st.setName("张三");
       st.setSex("F");
       st.setType("TRUE");
       students.add(st);
       st.setType("Flse");
       students.add(st);
       System.out.println(students);

   }
}

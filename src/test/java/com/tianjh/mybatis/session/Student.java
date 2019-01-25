package com.tianjh.mybatis.session;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/11
 * @since: 1 description:
 */
public class Student {
    private String name;
    private String sex;
    private Integer age;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Student{" +
            "name='" + name + '\'' +
            ", sex='" + sex + '\'' +
            ", age=" + age +
            ", type='" + type + '\'' +
            '}';
    }
}

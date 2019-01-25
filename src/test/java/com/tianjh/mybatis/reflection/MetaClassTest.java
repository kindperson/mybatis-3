package com.tianjh.mybatis.reflection;

import com.tianjh.mybatis.domain.misc.RichType;
import com.tianjh.mybatis.domain.misc.generics.GenericConcrete;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author: wb-tjh438466
 * @date: 2019/1/24
 * @since: 1 description:
 */
public class MetaClassTest {
    private RichType rich = new RichType();

    public MetaClassTest() {
        rich.setRichType(new RichType());
    }

    @Test
    public void shouldTestDataTypeOfGenericMethod() {
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaClass meta = MetaClass.forClass(GenericConcrete.class, reflectorFactory);
        System.out.println(Long.class);
        System.out.println(meta.getGetterType("id"));
        assertEquals(Long.class, meta.getGetterType("id"));
        assertEquals(Long.class, meta.getSetterType("id"));
    }

    @Test
    public void shouldCheckGetterExistance() {
        ReflectorFactory
            reflectorFactory = new DefaultReflectorFactory();
        MetaClass meta = MetaClass.forClass(RichType.class, reflectorFactory);
        assertTrue(meta.hasGetter("richField"));
        assertTrue(meta.hasGetter("richProperty"));
        assertTrue(meta.hasGetter("richList"));
        assertTrue(meta.hasGetter("richMap"));
        assertTrue(meta.hasGetter("richList[0]"));

        assertTrue(meta.hasGetter("richType"));
        assertTrue(meta.hasGetter("richType.richField"));
        assertTrue(meta.hasGetter("richType.richProperty"));
        assertTrue(meta.hasGetter("richType.richList"));
        assertTrue(meta.hasGetter("richType.richMap"));
        assertTrue(meta.hasGetter("richType.richList[0]"));

        assertFalse(meta.hasGetter("[0]"));
    }

    @Test
    public void shouldCheckSetterExistance() {
        org.apache.ibatis.reflection.ReflectorFactory
            reflectorFactory = new org.apache.ibatis.reflection.DefaultReflectorFactory();
        org.apache.ibatis.reflection.MetaClass meta = org.apache.ibatis.reflection.MetaClass.forClass(RichType.class,
            reflectorFactory);
        assertTrue(meta.hasSetter("richField"));
        assertTrue(meta.hasSetter("richProperty"));
        assertTrue(meta.hasSetter("richList"));
        assertTrue(meta.hasSetter("richMap"));
        assertTrue(meta.hasSetter("richList[0]"));

        assertTrue(meta.hasSetter("richType"));
        assertTrue(meta.hasSetter("richType.richField"));
        assertTrue(meta.hasSetter("richType.richProperty"));
        assertTrue(meta.hasSetter("richType.richList"));
        assertTrue(meta.hasSetter("richType.richMap"));
        assertTrue(meta.hasSetter("richType.richList[0]"));

        assertFalse(meta.hasSetter("[0]"));
    }

    @Test
    public void shouldCheckTypeForEachGetter() {
        org.apache.ibatis.reflection.ReflectorFactory
            reflectorFactory = new org.apache.ibatis.reflection.DefaultReflectorFactory();
        org.apache.ibatis.reflection.MetaClass meta = org.apache.ibatis.reflection.MetaClass.forClass(RichType.class,
            reflectorFactory);
        assertEquals(String.class, meta.getGetterType("richField"));
        assertEquals(String.class, meta.getGetterType("richProperty"));
        assertEquals(List.class, meta.getGetterType("richList"));
        assertEquals(Map.class, meta.getGetterType("richMap"));
        assertEquals(List.class, meta.getGetterType("richList[0]"));

        assertEquals(RichType.class, meta.getGetterType("richType"));
        assertEquals(String.class, meta.getGetterType("richType.richField"));
        assertEquals(String.class, meta.getGetterType("richType.richProperty"));
        assertEquals(List.class, meta.getGetterType("richType.richList"));
        assertEquals(Map.class, meta.getGetterType("richType.richMap"));
        assertEquals(List.class, meta.getGetterType("richType.richList[0]"));
    }

    @Test
    public void shouldCheckTypeForEachSetter() {
        org.apache.ibatis.reflection.ReflectorFactory
            reflectorFactory = new org.apache.ibatis.reflection.DefaultReflectorFactory();
        org.apache.ibatis.reflection.MetaClass meta = org.apache.ibatis.reflection.MetaClass.forClass(RichType.class,
            reflectorFactory);
        assertEquals(String.class, meta.getSetterType("richField"));
        assertEquals(String.class, meta.getSetterType("richProperty"));
        assertEquals(List.class, meta.getSetterType("richList"));
        assertEquals(Map.class, meta.getSetterType("richMap"));
        assertEquals(List.class, meta.getSetterType("richList[0]"));

        assertEquals(RichType.class, meta.getSetterType("richType"));
        assertEquals(String.class, meta.getSetterType("richType.richField"));
        assertEquals(String.class, meta.getSetterType("richType.richProperty"));
        assertEquals(List.class, meta.getSetterType("richType.richList"));
        assertEquals(Map.class, meta.getSetterType("richType.richMap"));
        assertEquals(List.class, meta.getSetterType("richType.richList[0]"));
    }

    @Test
    public void shouldCheckGetterAndSetterNames() {
        org.apache.ibatis.reflection.ReflectorFactory
            reflectorFactory = new org.apache.ibatis.reflection.DefaultReflectorFactory();
        org.apache.ibatis.reflection.MetaClass meta = org.apache.ibatis.reflection.MetaClass.forClass(RichType.class,
            reflectorFactory);
        assertEquals(5, meta.getGetterNames().length);
        assertEquals(5, meta.getSetterNames().length);
    }

    @Test
    public void shouldFindPropertyName() {
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaClass meta = MetaClass.forClass(RichType.class, reflectorFactory);
        assertEquals("richField", meta.findProperty("RICHfield"));
    }
}

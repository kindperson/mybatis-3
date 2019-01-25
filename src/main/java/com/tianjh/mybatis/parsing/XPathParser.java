package com.tianjh.mybatis.parsing;

import org.apache.ibatis.builder.BuilderException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.Reader;
import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public class XPathParser {
    /**
     * xml 文档
     */
    private Document document;
    private boolean validation;
    private EntityResolver entityResolver;
    /**
     * properties 文件配置属性
     */
    private Properties variables;
    private XPath xpath;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public boolean isValidation() {
        return validation;
    }

    public void setValidation(boolean validation) {
        this.validation = validation;
    }

    public EntityResolver getEntityResolver() {
        return entityResolver;
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    public Properties getVariables() {
        return variables;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public XPath getXpath() {
        return xpath;
    }

    public void setXpath(XPath xpath) {
        this.xpath = xpath;
    }

    public XPathParser(Reader reader, boolean validation, Properties variables, EntityResolver entityResolver) {
        commonConstructor(validation, variables, entityResolver);
        this.document = createDocument(new InputSource(reader));
    }

    private Document createDocument(InputSource inputSource) {
        return null;
    }

    /**
     * 设置XPathParser类的属性，这里创建了XPathFactory 赋值给xpath
     * @param validation
     * @param variables
     * @param entityResolver
     */
    private void commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver) {
        this.validation = validation;
        this.entityResolver = entityResolver;
        this.variables = variables;
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
    }

    /**
     * 把xml转成XNode格式
     * @param expression
     * @return
     */
    public XNode evalNode(String expression) {
        return evalNode(document, expression);
    }


    public XNode evalNode(Object root, String expression) {
        Node node = (Node)evaluate(expression, root, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new XNode(this, node, variables);
    }

    private Object evaluate(String expression, Object root, QName returnType) {
        try {
            return xpath.evaluate(expression, root, returnType);
        } catch (Exception e) {
            throw new BuilderException("Error evaluating XPath.  Cause: " + e, e);
        }
    }

}

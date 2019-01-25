package com.tianjh.mybatis.builder.xml;

import com.tianjh.mybatis.builder.BaseBuilder;
import com.tianjh.mybatis.executor.ErrorContext;
import com.tianjh.mybatis.parsing.XNode;
import com.tianjh.mybatis.parsing.XPathParser;
import com.tianjh.mybatis.reflection.DefaultReflectorFactory;
import com.tianjh.mybatis.reflection.MetaClass;
import com.tianjh.mybatis.reflection.ReflectorFactory;
import com.tianjh.mybatis.session.Configuration;
import org.apache.ibatis.builder.BuilderException;

import java.io.Reader;
import java.util.Properties;

/**
 * @author: wb-tjh438466
 * @date: 2018/12/5
 * @since: 1 description:
 */
public class XMLConfigBuilder extends BaseBuilder {

    /**
     * 是否初始化过XMLConfigBuilder,确保这个文件只初始化一次
     */
    private Boolean parsed;
    private String environment;
    private XPathParser parser;
    private ReflectorFactory localReflectorFactory = new DefaultReflectorFactory();
    public XMLConfigBuilder(Reader reader, String environment, Properties props) {
        this(new XPathParser(reader, true, props, new XMLMapperEntityResolver()), environment, props);
    }

    private XMLConfigBuilder(XPathParser parser, String environment, Properties props) {
        super(new Configuration());
        ErrorContext.instance().resource("SQL Mapper Configuration");
        this.configuration.setVariables(props);
        this.parsed = false;
        this.environment = environment;
        this.parser = parser;
    }

    public Configuration parse() {
        if (parsed) {
            throw new BuilderException("Each XMLConfigBuilder can only be used once.");
        }
        parsed = true;
        parseConfiguration(parser.evalNode("/configuration"));
        return configuration;
    }

    private void parseConfiguration(XNode root) {
        try {
            Properties settings = settingsAsPropertiess(root.evalNode("settings"));
            //issue #117 read properties first
          /*  propertiesElement(root.evalNode("properties"));
            loadCustomVfs(settings);
            typeAliasesElement(root.evalNode("typeAliases"));
            pluginElement(root.evalNode("plugins"));
            objectFactoryElement(root.evalNode("objectFactory"));
            objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
            reflectionFactoryElement(root.evalNode("reflectionFactory"));
            settingsElement(settings);
            // read it after objectFactory and objectWrapperFactory issue #631
            environmentsElement(root.evalNode("environments"));
            databaseIdProviderElement(root.evalNode("databaseIdProvider"));
            typeHandlerElement(root.evalNode("typeHandlers"));
            mapperElement(root.evalNode("mappers"));*/
        } catch (Exception e) {
            throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
    }

    /**
     * 解析setting
     * @param context
     * @return
     */
    private Properties settingsAsPropertiess(XNode context) {
        if (context == null) {
            return new Properties();
        }
        Properties props = context.getChildrenAsProperties();
        // Check that all settings are known to the configuration class
       MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
        for (Object key : props.keySet()) {
            if (!metaConfig.hasSetter(String.valueOf(key))) {
                throw new BuilderException("The setting " + key + " is not known.  Make sure you spelled it correctly  (case sensitive).");
            }
        }
        return props;
    }

  /*  private void propertiesElement(XNode context) throws Exception {
        if (context != null) {
            Properties defaults = context.getChildrenAsProperties();
            String resource = context.getStringAttribute("resource");
            String url = context.getStringAttribute("url");
            if (resource != null && url != null) {
                throw new BuilderException("The properties element cannot specify both a URL and a resource based property file reference.  Please specify one or the other.");

            }
            if (resource != null) {
                defaults.putAll(Resources.getResourceAsProperties(resource));
            } else if (url != null) {
                defaults.putAll(Resources.getUrlAsProperties(url));
            }
            Properties vars = configuration.getVariables();
            if (vars != null) {
                defaults.putAll(vars);
            }
            parser.setVariables(defaults);
            configuration.setVariables(defaults);
        }
    }*/
}

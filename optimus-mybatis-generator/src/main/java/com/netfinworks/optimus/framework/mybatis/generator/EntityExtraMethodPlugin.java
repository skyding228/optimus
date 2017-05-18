package com.netfinworks.optimus.framework.mybatis.generator;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class EntityExtraMethodPlugin extends PluginAdapter{

    public boolean validate(List<String> warnings) {
        return true;
    }
    
    /**
     * 生成实体额外的方法
     * public Class<?> getEntityMapperClass(){
     *      return entityNameMapper.class;
     * }
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Method method = new Method();
        method.addAnnotation("@Override");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType("Class<?>"));
        method.setName("getEntityMapperClass");
        String entityName = topLevelClass.getType().getShortName();
        method.addBodyLine("return " + entityName + "Mapper.class;");
        topLevelClass.addMethod(method);
        
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

}

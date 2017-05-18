package com.netfinworks.optimus.framework.mybatis.generator;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 实体注释类插件
 * @author baodk
 */
public class EntityCommentPlugin extends PluginAdapter{
    
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
      //生成类注释
////        String tableComment = introspectedTable.getTableConfiguration().getTableComment();
//        topLevelClass.addJavaDocLine("/**");
//        topLevelClass.addJavaDocLine(" * " + (tableComment == null || "".equals(tableComment) ? "" : tableComment));
//        topLevelClass.addJavaDocLine(" */");
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }
    
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
      //生成字段注释
        String remarks = introspectedColumn.getRemarks();
        field.addJavaDocLine("/** " + (null == remarks || "".equals(remarks) ? "" : remarks) + " */");
        return super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }
    
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //生成注释
        String remarks = introspectedColumn.getRemarks();
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + (null == remarks || "".equals(remarks) ? "" : remarks));
        method.addJavaDocLine(" * @param " + introspectedColumn.getJavaProperty() + "");
        method.addJavaDocLine(" */");
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
                modelClassType);

    }
    
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
      //生成注释
        String remarks = introspectedColumn.getRemarks();
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + (null == remarks || "".equals(remarks) ? "" : remarks));
        method.addJavaDocLine(" * @return " + introspectedColumn.getJavaProperty() + "");
        method.addJavaDocLine(" */");
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable,
                modelClassType);
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

}

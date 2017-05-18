package com.netfinworks.optimus.framework.mybatis.generator;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 分页插件
 * @author baodekang
 *
 */
public class PaginationPlugin extends PluginAdapter{
	
	private static final String LIMIT_START = "limitStart";
	private static final String LIMIT_END = "limitEnd";
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		addLimit(topLevelClass, introspectedTable, LIMIT_START);
		addLimit(topLevelClass, introspectedTable, LIMIT_END);
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}
	
	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
			XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");//$NON-NLS-1$  
		isNotNullElement.addAttribute(new Attribute("test", LIMIT_START + " != null and " + LIMIT_START + " >= 0"));//$NON-NLS-1$ //$NON-NLS-2$  
		isNotNullElement.addElement(new TextElement("limit #{" + LIMIT_START +"}, #{" + LIMIT_END + "}"));
		element.addElement(isNotNullElement);
		
		return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element,
				introspectedTable);
	}
	
	private void addLimit(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name){
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(PrimitiveTypeWrapper.getIntegerInstance());
		field.setName(name);
		
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(PrimitiveTypeWrapper.getIntegerInstance(), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(PrimitiveTypeWrapper.getIntegerInstance());
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

}

package com.lms.generate.generator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import com.lms.contants.FieldTypeEnum;
import com.lms.generate.model.TableSchema;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author lms2000
 */
public class BasicCodeGenerator extends AbstractGenerator {

    @Override
    public void run(String packageName, Object... args) throws TemplateException, IOException {
       TableSchema tableSchema= (TableSchema) args[0];
        for (TableSchema.Table table : tableSchema.getTableList()) {
            generateOneTable(table,packageName);
        }
    }


    private void generateOneTable(TableSchema.Table table,String packageName) throws TemplateException, IOException {
       String baseOutput=System.getProperty("user.dir")+"/generated";
       String baseInput=System.getProperty("user.dir")+"/src/main/resources/templates";
       String classTypeName = StringUtils.capitalize(StrUtil.toCamelCase(table.getTableName()));
       String className=StringUtils.uncapitalize(StrUtil.toCamelCase(table.getTableName()));
       String classComment = Optional.ofNullable(table.getTableComment()).orElse(className);
        List<TableSchema.Field> fieldList = table.getFieldList();
        for (TableSchema.Field field : fieldList) {
            // 修改为java的类型
            String fieldType =field.getFieldType().replaceAll("\\(\\d+\\)", "") ;
            String fieldName = StringUtils.uncapitalize(StrUtil.toCamelCase(field.getFieldName()));
            // 判断是否是id createTime updateTime
            if(exclusionsField(fieldName)){
                field.setIsGenerated(false);
            }
            FieldTypeEnum fieldTypeEnum = Optional.ofNullable(FieldTypeEnum.getEnumByValue(fieldType)).orElse(FieldTypeEnum.TEXT);
            field.setFieldName(fieldName);
            field.setFieldType(fieldTypeEnum.getJavaType());
        }
        // 获取填充的数据
        Map<String,Object> modelMap=new HashMap<>(11);
        modelMap.put("author","LMS2000");
        modelMap.put("createDate", DateUtil.today());
        modelMap.put("packageName",packageName);
        modelMap.put("classTypeName",classTypeName);
        modelMap.put("className",className);
        modelMap.put("fieldList",table.getFieldList());
        modelMap.put("classComment",classComment);
        modelMap.put("tableName",table.getTableName());
        // 生成文件

        // 生成VO
        String inputPath=baseInput+"/vo/EntityVO.java.ftl";
        String outputPath=baseOutput+"/vo/"+classTypeName+"VO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成DTO
        inputPath=baseInput+"/dto/CreateEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Create"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+"/dto/UpdateEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Update"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+"/dto/QueryEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Query"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成Mapper
        inputPath=baseInput+"/mapper/Mapper.java.ftl";
        outputPath=baseOutput+"/mapper/"+classTypeName+"Mapper.java";
        writeToTemplate(modelMap,inputPath,outputPath);
        // 生成service层代码
        inputPath=baseInput+"/service/IService.java.ftl";
        outputPath=baseOutput+"/service/"+classTypeName+"Service.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+"/service/impl/ServiceImpl.java.ftl";
        outputPath=baseOutput+"/service/impl/"+classTypeName+"ServiceImpl.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        //生成controller层代码

        inputPath=baseInput+"/controller/Controller.java.ftl";
        outputPath=baseOutput+"/controller/"+classTypeName+"Controller.java";
        writeToTemplate(modelMap,inputPath,outputPath);


        // 生成实体类
        inputPath=baseInput+"/entity/Entity.java.ftl";
        outputPath=baseOutput+"/entity/"+classTypeName+".java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成常量类
        inputPath=baseInput+"/constant/SqlConstant.java.ftl";
        outputPath=baseOutput+"/constant/SqlConstant.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成工厂类

        inputPath=baseInput+"/factory/EntityFactory.java.ftl";
        outputPath=baseOutput+"/factory/"+classTypeName+"Factory.java";
        writeToTemplate(modelMap,inputPath,outputPath);
    }

    private boolean exclusionsField(String fieldName){
        List<String> exclustionList = Arrays.asList("id", "createTime", "updateTime");
        return exclustionList.contains(fieldName);
    }
}

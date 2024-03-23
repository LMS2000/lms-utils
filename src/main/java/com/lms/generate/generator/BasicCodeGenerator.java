package com.lms.generate.generator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import com.lms.contants.FieldTypeEnum;
import com.lms.generate.model.TableSchema;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author lms2000
 */
public class BasicCodeGenerator extends AbstractGenerator {

    @Override
    public void run(String packageName, Object... args) throws TemplateException, IOException {
       TableSchema tableSchema= (TableSchema) args[0];
       String path = (String) args[1];
        for (TableSchema.Table table : tableSchema.getTableList()) {
            generateOneTable(table,packageName,path);
        }
    }


    /**
     *
     * @param table
     * @param packageName
     * @param outputDir
     * @throws TemplateException
     * @throws IOException
     */
    private void generateOneTable(TableSchema.Table table,String packageName,String outputDir) throws TemplateException, IOException {
       String baseOutput=outputDir;
       String baseInput= "";

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
        String inputPath=baseInput+ File.separator+"templates/vo/EntityVO.java.ftl";
        String outputPath=baseOutput+"/vo/"+classTypeName+"VO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成DTO
        inputPath=baseInput+File.separator+"templates/dto/CreateEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Create"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+File.separator+"templates/dto/UpdateEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Update"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+File.separator+"templates/dto/QueryEntityDTO.java.ftl";
        outputPath=baseOutput+"/dto/Query"+classTypeName+"DTO.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成Mapper
        inputPath=baseInput+File.separator+"templates/mapper/Mapper.java.ftl";
        outputPath=baseOutput+"/mapper/"+classTypeName+"Mapper.java";
        writeToTemplate(modelMap,inputPath,outputPath);
        // 生成service层代码
        inputPath=baseInput+File.separator+"templates/service/IService.java.ftl";
        outputPath=baseOutput+"/service/"+classTypeName+"Service.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        inputPath=baseInput+File.separator+"templates/service/impl/ServiceImpl.java.ftl";
        outputPath=baseOutput+"/service/impl/"+classTypeName+"ServiceImpl.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        //生成controller层代码

        inputPath=baseInput+File.separator+"templates/controller/Controller.java.ftl";
        outputPath=baseOutput+"/controller/"+classTypeName+"Controller.java";
        writeToTemplate(modelMap,inputPath,outputPath);


        // 生成实体类
        inputPath=baseInput+File.separator+"templates/entity/Entity.java.ftl";
        outputPath=baseOutput+"/entity/"+classTypeName+".java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成常量类
        inputPath=baseInput+File.separator+"templates/constant/SqlConstant.java.ftl";
        outputPath=baseOutput+"/constant/SqlConstant.java";
        writeToTemplate(modelMap,inputPath,outputPath);

        // 生成工厂类

        inputPath=baseInput+File.separator+"templates/factory/EntityFactory.java.ftl";
        outputPath=baseOutput+"/factory/"+classTypeName+"Factory.java";
        writeToTemplate(modelMap,inputPath,outputPath);
    }

    private boolean exclusionsField(String fieldName){
        List<String> exclustionList = Arrays.asList("id", "createTime", "updateTime");
        return exclustionList.contains(fieldName);
    }
}

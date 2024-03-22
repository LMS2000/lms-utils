package com.lms.generate.model;

import lombok.Data;

import java.util.List;

/**
 * 表概要
 *
 */
@Data
public class TableSchema {
    private List<Table> tableList;

    @Data
    public static class Table{
        /**
         * 表名
         */
        private String tableName;

        /**
         * 表注释
         */
        private String tableComment;


        /**
         * 列信息列表
         */
        private List<Field> fieldList;
    }


    /**
     * 列信息
     */
    @Data
    public static class Field {
        /**
         * 字段名
         */
        private String fieldName;

        /**
         * 字段类型
         */
        private String fieldType;

        /**
         * 注释（字段中文名）
         */
        private String comment;

        private Boolean isGenerated=true;
    }
}

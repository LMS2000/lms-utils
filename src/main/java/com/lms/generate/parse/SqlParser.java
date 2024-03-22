package com.lms.generate.parse;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;

import com.lms.generate.model.TableSchema;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {
    private static final MySQLDialect sqlDialect = new MySQLDialect();
    public static TableSchema buildFromSql(String sql) {

        try {
            List<TableSchema.Table> tableList=new ArrayList<>();
            TableSchema tableSchema = new TableSchema();
            String regex = "CREATE TABLE [^;]+;";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sql);
            while (matcher.find()) {
                String createTableStatement = matcher.group();
                if(StringUtils.isBlank(createTableStatement)) {
                    continue;
                }
                // 解析 SQL
                MySqlCreateTableParser parser = new MySqlCreateTableParser(createTableStatement);
                SQLCreateTableStatement sqlCreateTableStatement = parser.parseCreateTable();
                TableSchema.Table table=new TableSchema.Table();
                table.setTableName(sqlDialect.parseTableName(sqlCreateTableStatement.getTableName()));
                String tableComment = null;
                if (sqlCreateTableStatement.getComment() != null) {
                    tableComment = sqlCreateTableStatement.getComment().toString();
                    if (tableComment.length() > 2) {
                        tableComment = tableComment.substring(1, tableComment.length() - 1);
                    }
                }
                table.setTableComment(tableComment);
                List<TableSchema.Field> fieldList = new ArrayList<>();
                // 解析列
                for (SQLTableElement sqlTableElement : sqlCreateTableStatement.getTableElementList()) {
                    // 主键约束
                    if (sqlTableElement instanceof SQLPrimaryKey) {
                        SQLPrimaryKey sqlPrimaryKey = (SQLPrimaryKey) sqlTableElement;
                        String primaryFieldName = sqlDialect.parseFieldName(sqlPrimaryKey.getColumns().get(0).toString());
                    } else if (sqlTableElement instanceof SQLColumnDefinition) {
                        // 列
                        SQLColumnDefinition columnDefinition = (SQLColumnDefinition) sqlTableElement;
                        TableSchema.Field field = new TableSchema.Field();
                        field.setFieldName(sqlDialect.parseFieldName(columnDefinition.getNameAsString()));
                        field.setFieldType(columnDefinition.getDataType().toString());
                        String defaultValue = null;
                        if (columnDefinition.getDefaultExpr() != null) {
                            defaultValue = columnDefinition.getDefaultExpr().toString();
                        }
                        String comment = null;
                        if (columnDefinition.getComment() != null) {
                            comment = columnDefinition.getComment().toString();
                            if (comment.length() > 2) {
                                comment = comment.substring(1, comment.length() - 1);
                            }
                        }
                        field.setComment(comment);
                        fieldList.add(field);
                    }
                }
                table.setFieldList(fieldList);
                tableList.add(table);
            }
            tableSchema.setTableList(tableList);
            return tableSchema;
        } catch (Exception e) {
              e.printStackTrace();
        }
        return null;
    }


}

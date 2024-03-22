package com.lms;

import cn.hutool.json.JSONUtil;
import com.lms.generate.generator.BasicCodeGenerator;
import com.lms.generate.model.TableSchema;
import com.lms.generate.parse.SqlParser;
import com.lms.redis.RedisCache;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;


public class UtilTestApp {

    @Test
    public void test() throws TemplateException, IOException {
        String sql="CREATE TABLE `report` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                "  `content` text NOT NULL COMMENT '内容',\n" +
                "  `type` int(11) NOT NULL COMMENT '举报实体类型（0-词库）',\n" +
                "  `reported_id` bigint(20) NOT NULL COMMENT '被举报对象 id',\n" +
                "  `reported_user_id` bigint(20) NOT NULL COMMENT '被举报用户 id',\n" +
                "  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-未处理, 1-已处理）',\n" +
                "  `user_id` bigint(20) NOT NULL COMMENT '创建用户 id',\n" +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报';" +
                "CREATE TABLE `table_info` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                "  `name` varchar(512) DEFAULT NULL COMMENT '名称',\n" +
                "  `content` text COMMENT '表信息（json）',\n" +
                "  `review_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-待审核, 1-通过, 2-拒绝）',\n" +
                "  `review_message` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核信息',\n" +
                "  `user_id` bigint(20) NOT NULL COMMENT '创建用户 id',\n" +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  KEY `idx_name` (`name`)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='表信息';";
        TableSchema tableSchema = SqlParser.buildFromSql(sql);
        assert tableSchema != null;
        for (TableSchema.Table table : tableSchema.getTableList()) {
            System.out.println(JSONUtil.toJsonStr(table));
        }
        BasicCodeGenerator basicCodeGenerator=new BasicCodeGenerator();
        basicCodeGenerator.run("com.lms.sqlfather.generated",tableSchema);
    }
}

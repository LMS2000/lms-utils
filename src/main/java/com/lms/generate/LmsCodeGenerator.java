package com.lms.generate;


import com.lms.generate.generator.BasicCodeGenerator;
import com.lms.generate.model.TableSchema;
import com.lms.generate.parse.SqlParser;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author lms2000
 */
public class LmsCodeGenerator {


    public static  void doGenerate(String packageName,String sql){
        TableSchema tableSchema = SqlParser.buildFromSql(sql);
        BasicCodeGenerator basicCodeGenerator=new BasicCodeGenerator();

        try {
            basicCodeGenerator.run(packageName,tableSchema);
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}

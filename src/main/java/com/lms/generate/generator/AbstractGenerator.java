package com.lms.generate.generator;


import cn.hutool.core.io.FileUtil;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author ccy
 */
@Data
public abstract class AbstractGenerator {

	private static final Logger log = LoggerFactory.getLogger(AbstractGenerator.class);
	/**
	 * 处理生成
	 */
	public abstract void run(String packageName,Object... args) throws TemplateException, IOException;

	/**
	 * 填充模板并且生成文件
	 *
	 * @param dataModel
	 * @param relativeInputPath
	 * @param outputPath
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected void writeToTemplate(Map<String, Object> dataModel, String relativeInputPath, String outputPath) throws TemplateException, IOException {
		// new 出 Configuration 对象，参数为 FreeMarker 版本号
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);;

		// 获取模板文件所属包和模板名称
		int lastSplitIndex = relativeInputPath.lastIndexOf("/");
		String basePackagePath = relativeInputPath.substring(0, lastSplitIndex);
		String templateName = relativeInputPath.substring(lastSplitIndex + 1);

		// 通过类加载器读取模板
		ClassTemplateLoader templateLoader = new ClassTemplateLoader(AbstractGenerator.class, basePackagePath);
		configuration.setTemplateLoader(templateLoader);

		// 设置模板文件使用的字符集
		configuration.setDefaultEncoding("utf-8");

		// 创建模板对象，加载指定模板
		Template template = configuration.getTemplate(templateName);

		// 文件不存在则创建文件和父目录
		if (!FileUtil.exist(outputPath)) {
			FileUtil.touch(outputPath);
		}

		// 生成
		Writer out = new FileWriter(outputPath);
		template.process(dataModel, out);

		// 生成文件后别忘了关闭哦
		out.close();
	}

}

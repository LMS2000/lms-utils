import ${packageName}.entity.${classTypeName};
import ${packageName}.vo.${classTypeName}VO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* ${className}字段转换类
* @author ${author}
* @since ${createDate}
*/
public class ${classTypeName}Factory {
public static final ${classTypeName}Factory.${classTypeName}Converter ${classTypeName?upper_case}_CONVERTER = Mappers.getMapper(${classTypeName}Factory.${classTypeName}Converter.class);

@Mapper
public interface ${classTypeName}Converter {
@Mappings({

})
${classTypeName}VO to${classTypeName}VO(${classTypeName} entity);

List<${classTypeName}VO> toList${classTypeName}VO(List<${classTypeName}> entityList);
        }

        }

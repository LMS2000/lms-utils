package ${packageName}.dto;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
* ${classComment}
* @author ${author}
* @since ${createDate}
*/
@Data
@ApiModel(value = "Update${classTypeName}DTO对象", description = "${classComment}")
public class Update${classTypeName}DTO implements Serializable {

<#-- 序列化 -->
private static final long serialVersionUID = 1L;

<#-- 循环生成字段 ---------->
<#list fieldList as field>
    <#if field.comment!?length gt 0>
        /**
        * ${field.comment}
        */
    </#if>
    private ${field.fieldType} ${field.fieldName};

</#list>
}

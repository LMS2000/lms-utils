package ${packageName}.vo;
import lombok.Data;
import com.lms.common.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
* ${classComment}
* @author ${author}
* @since ${createDate}
*/
@Data
@ApiModel(value = "${classTypeName}VO对象", description = "${classComment}")
public class ${classTypeName}VO extends BaseVO {



<#-- 循环生成字段 ---------->
<#list fieldList as field>
     <#if field.isGenerated?? && field.isGenerated>
         <#if field.comment!?length gt 0>
             /**
             * ${field.comment}
             */
         </#if>
         @ApiModelProperty(value = "${field.comment}")
         private ${field.fieldType} ${field.fieldName};
     </#if>


</#list>
}

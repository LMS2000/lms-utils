package ${packageName}.vo;
import lombok.*;
import com.lms.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
/**
* ${classComment}
* @author ${author}
* @since ${createDate}
*/
@EqualsAndHashCode(callSuper = true)
@TableName(value ="${tableName}")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ${classTypeName} extends BaseEntity {

<#-- 序列化 -->
private static final long serialVersionUID = 1L;

<#-- 循环生成字段 ---------->
<#list fieldList as field>
    <#if field.isGenerated?? && field.isGenerated>
        <#if field.comment!?length gt 0>
            /**
            * ${field.comment}
            */
        </#if>
        private ${field.fieldType} ${field.fieldName};
    </#if>


</#list>
}

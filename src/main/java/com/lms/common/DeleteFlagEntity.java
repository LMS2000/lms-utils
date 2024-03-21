package com.lms.common;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lms2000
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteFlagEntity extends BaseEntity{

    /**
     * 删除标记
     */
    @TableField("is_delete")
    @TableLogic
    protected Integer deleteFlag;
}

package com.lms.page;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomPage {

    //如果说请求的这两个字段为默认值说明前端没有设置
    @ApiModelProperty("第几页")
    private Integer pageSize = -1;
    @ApiModelProperty("一页的数据条数")
    private Integer pageNum = -1;

    //判断这个分页请求设置有没有问题，可不可用
    public static boolean enable(CustomPage customPage) {
        return customPage != null && customPage.pageNum != null && customPage.getPageSize() != null
                && customPage.getPageNum() >= 0 && customPage.getPageSize() >= 0;
    }


    public static <T> List<T> getPageResult(CustomPage customPage, T pageType , IService<T> service, QueryWrapper<T> wrapper){
          List<T> res=null;
          //如果说分页请求信息有效就执行分页，否则就根据条件查找列表集合
         if(enable(customPage)){
             Page<T> page = service.page(PageFactory.newPage(pageType, customPage.getPageNum(), customPage.getPageSize()), wrapper);
             res=page.getRecords();
             PageStorage.exposePageInfo(page);
         }else{
             res=service.list(wrapper);
         }
         return res;
    }


}

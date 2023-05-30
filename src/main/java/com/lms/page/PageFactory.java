package com.lms.page;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

//获取page类
public class PageFactory {
    public static final Integer DEFAULT_PAGE_SIZE=10;
    public static final Integer DEFAULT_PAGE_NUM=0;




    public static <T> Page<T> newPage(T type,Integer pageNum,Integer pageSize){
       return new Page<T>(pageNum,pageSize);
    }

    public static  <T> Page<T> newPage(T type,Integer pageNum){
       return new Page<T>(pageNum,DEFAULT_PAGE_SIZE);
    }

    public static <T> Page<T> newPage(T type){
        return new Page<T>(DEFAULT_PAGE_NUM,DEFAULT_PAGE_SIZE);
    }

    public static <T> Page<T> newPage(T type,CustomPage customPage){
         return new Page<>(DEFAULT_PAGE_NUM,customPage.getPageSize());
    }



}

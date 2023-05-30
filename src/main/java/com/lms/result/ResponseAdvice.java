package com.lms.result;

import cn.hutool.db.PageResult;
import com.lms.page.PageContext;
import com.lms.page.PageStorage;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice(annotations = EnableResponseAdvice.class)
public class ResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {


       PageStorage pageInfo = PageContext.getPageInfo();
        if(pageInfo!=null && body instanceof List){
            pageInfo.setData((List<Object>) body);
            //请求结束,清除分页信息
            PageContext.clear();
            return ResultData.success(pageInfo);
        }
        // 如果说controller接口返回的数据已经是ResultData类型了就不再封装返回
        if(body instanceof ResultData){
           return body;
       }
       return ResultData.success(body);
    }
}

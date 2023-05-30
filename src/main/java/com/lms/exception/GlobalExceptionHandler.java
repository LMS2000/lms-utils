package com.lms.exception;


import com.lms.contants.HttpCode;
import com.lms.result.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数异常
     * @param e
     * @return
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultData illegalArgumentException(IllegalArgumentException e){
         log.error("出现参数异常",e);
         return ResultData.error(HttpCode.PARAMS_ERROR,e.getMessage(),e.getMessage());
    }

    /**
     * JSR303参数校验异常
     * @param e
     * @return
     */


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现了问题{},异常类型为{}",e.getMessage(),e.getClass());
          //获取绑定信息
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> map=new HashMap<>();
        bindingResult.getFieldErrors().forEach((error)->{
            map.put(error.getField(),error.getDefaultMessage());
        });
        return ResultData.error(HttpCode.PARAMS_ERROR,"数据校验出现问题",map);
    }

    @ExceptionHandler(Exception.class)
    public ResultData exception(Exception e) {
        log.error("错误类型为Exception : "+e);
        return ResultData.error(e.getMessage());
    }




}

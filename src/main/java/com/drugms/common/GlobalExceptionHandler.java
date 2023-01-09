package com.drugms.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * SQL错误
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg=split[2]+"已存在";
            return R.error(msg);
        }
        return R.error("失败了");
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public R<String> exceptionHandler(SQLSyntaxErrorException ex){
        log.error(ex.getMessage());
        return R.error("sql语法错误");
    }

    @ExceptionHandler(SQLException.class)
    public R<String> exceptionHandler(SQLException ex){
        log.error(ex.getMessage());
        return R.error("sql错误");
    }

    @ExceptionHandler(NullPointerException.class)
    public R<String> exceptionHandler(NullPointerException ex){
        log.error(ex.getMessage());
        return R.error("传参异常");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

}

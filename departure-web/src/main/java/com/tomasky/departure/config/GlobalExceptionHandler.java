package com.tomasky.departure.config;

import com.tomasky.departure.common.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by sam on 2020-01-02.17:32
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public Object validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        StringBuilder errorMessageBuilder = new StringBuilder("参数校验失败：");

        String msg;
        if (bindResult != null && bindResult.hasErrors()) {
            List<ObjectError> errorList = bindResult.getAllErrors();
            for (ObjectError error : errorList) {
                errorMessageBuilder.append(error.getDefaultMessage() + "；");
            }
            msg = errorMessageBuilder.toString();
            if (msg.contains("NumberFormatException")) {
                msg = "参数类型错误！";
            }
        } else {
            msg = "系统繁忙，请稍后重试...";
        }
        return CommonUtils.setErrorInfo(msg);
    }

}

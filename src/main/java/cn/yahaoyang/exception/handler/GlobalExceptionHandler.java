package cn.yahaoyang.exception.handler;

import cn.yahaoyang.domain.Result;
import cn.yahaoyang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> handleAuthException(BusinessException businessException){
        log.error("业务异常:{}",businessException.getMessage());
        return new Result<>(
                businessException.getCode(),
                businessException.getMessage(),null);
    }
}
package cn.yahaoyang.exception;

import cn.yahaoyang.utils.ResultUtil;
import lombok.*;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    private String message;

    private Integer code;

    public BusinessException(String message) {
        this.message = message;
        this.code = ResultUtil.ERROR_CODE;
    }
}

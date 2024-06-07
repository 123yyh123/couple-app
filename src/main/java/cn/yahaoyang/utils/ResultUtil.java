package cn.yahaoyang.utils;

import cn.yahaoyang.domain.Result;

/**
 * @author yyh
 * @date 2024/6/7
 */
public class ResultUtil {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer ERROR_CODE = 500;
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, SUCCESS, data);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }
}

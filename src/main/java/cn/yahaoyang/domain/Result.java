package cn.yahaoyang.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  Result<T>  implements Serializable {
    private Integer code;
    private String message;
    private T data;
}

package cn.yahaoyang.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
    private Date updateTime;
}


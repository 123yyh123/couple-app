package cn.yahaoyang.vo;

import cn.yahaoyang.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO extends User {
    private String code;
    private String token;
}
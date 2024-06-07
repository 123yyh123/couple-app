package cn.yahaoyang.controller;

import cn.yahaoyang.domain.Result;
import cn.yahaoyang.domain.User;
import cn.yahaoyang.exception.BusinessException;
import cn.yahaoyang.service.UserService;
import cn.yahaoyang.utils.ParameterVerificationUtil;
import cn.yahaoyang.utils.ResultUtil;
import cn.yahaoyang.vo.RegisterUserVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

/**
 * @author yyh
 * @date 2024/6/7
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        verify(user);
        return ResultUtil.success(userService.login(user));
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody RegisterUserVO user){
        verify(user);
        if (!ParameterVerificationUtil.isHasText(user.getCode())){
            throw new BusinessException("验证码不能为空");
        }
        return ResultUtil.success(userService.register(user));
    }

    private void verify(User user){
        if (ParameterVerificationUtil.isNull(user)){
            throw new BusinessException("用户信息不能为空");
        }
        if (!ParameterVerificationUtil.isHasText(user.getEmail())){
            throw new BusinessException("邮箱不能为空");
        }
        if (!ParameterVerificationUtil.isHasText(user.getPassword())){
            throw new BusinessException("密码不能为空");
        }
    }
}

package cn.yahaoyang.controller;

import cn.yahaoyang.domain.Result;
import cn.yahaoyang.service.SendEmailService;
import cn.yahaoyang.utils.ParameterVerificationUtil;
import cn.yahaoyang.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yyh
 * @date 2024/6/7
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    public final SendEmailService sendEmailService;

    @PostMapping("/send/{email}")
    public Result<Boolean> sendEmail(@PathVariable String email){
        if(!ParameterVerificationUtil.isEmail(email)){
            return ResultUtil.error("邮箱格式不正确");
        }
        return ResultUtil.success(sendEmailService.sendCode(email));
    }
}

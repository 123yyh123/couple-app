package cn.yahaoyang.service.impl;

import cn.yahaoyang.constant.RedisConstant;
import cn.yahaoyang.exception.BusinessException;
import cn.yahaoyang.service.SendEmailService;
import cn.yahaoyang.utils.RandomUtil;
import cn.yahaoyang.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    private final RedisCache redisCache;

    private final TemplateEngine templateEngine;

    @Override
    public boolean sendCode(String email) {
        String s = (String) redisCache.get(RedisConstant.EMAIL_REGISTER_CODE + email);
        if (s != null) {
            long expire = redisCache.getExpire(RedisConstant.EMAIL_REGISTER_CODE + email);
            if (expire > 0){
                throw new BusinessException("验证码已发送，请稍后再试");
            }
        }
        String code = RandomUtil.getRandomCode(6);
        Context context = new Context();
        context.setVariable("verifyCode", Arrays.asList(code.split("")));
        String process = templateEngine.process("VerificationCode", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("验证码");
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSentDate(new Date());
            mimeMessageHelper.setText(process, true);
            javaMailSender.send(mimeMessage);
            redisCache.set(RedisConstant.EMAIL_REGISTER_CODE + email, code, 300);
        } catch (Exception e) {
            throw new BusinessException("发送邮件失败");
        }
        return true;
    }
}

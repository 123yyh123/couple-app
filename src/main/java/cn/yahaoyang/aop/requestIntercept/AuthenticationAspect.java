package cn.yahaoyang.aop.requestIntercept;

import cn.yahaoyang.config.JwtProperties;
import cn.yahaoyang.constant.RedisConstant;
import cn.yahaoyang.exception.BusinessException;
import cn.yahaoyang.utils.JWTUtil;
import cn.yahaoyang.utils.ParameterVerificationUtil;
import cn.yahaoyang.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yyh
 * @date 2024-06-08
 */
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationAspect {
    private final JwtProperties jwtProperties;
    private final RedisCache redisCache;
    private final HttpServletRequest request;

    @Pointcut("@annotation(authentication)")
    public void authenticationPointcut(Authentication authentication) {}

    @Before(value = "authenticationPointcut(authentication)", argNames = "authentication")
    public void before(Authentication authentication) {
        String token = request.getHeader("token");
        if (!ParameterVerificationUtil.isHasText(token)) {
            throw new BusinessException("请先登录");
        }
        try {
            Integer userId = JWTUtil.getCurrentUserId(token);
            long time = (Long) redisCache.get(RedisConstant.REDIS_KEY_USER_LOGIN_EXPIRE + userId);
            if (System.currentTimeMillis() > time) {
                throw new BusinessException("token已过期");
            }
            // token续期
            if (time - System.currentTimeMillis() < jwtProperties.getExpireTime() / 2) {
                redisCache.set(RedisConstant.REDIS_KEY_USER_LOGIN_EXPIRE + userId, System.currentTimeMillis() + jwtProperties.getExpireTime());
            }
        } catch (BusinessException e) {
            throw new BusinessException("登录状态异常");
        }
    }

}

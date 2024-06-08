package cn.yahaoyang.service.impl;

import cn.yahaoyang.Mapper.UserMapper;
import cn.yahaoyang.config.JwtProperties;
import cn.yahaoyang.constant.RedisConstant;
import cn.yahaoyang.domain.User;
import cn.yahaoyang.exception.BusinessException;
import cn.yahaoyang.service.UserService;
import cn.yahaoyang.utils.JWTUtil;
import cn.yahaoyang.utils.ParameterVerificationUtil;
import cn.yahaoyang.utils.RedisCache;
import cn.yahaoyang.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    public final RedisCache redisCache;
    public static JwtProperties jwtProperties;

    @Override
    public UserVO login(User user) {
        User loginUser = this.baseMapper.selectByEmail(user.getEmail());
        if (ParameterVerificationUtil.isNull(loginUser)) {
            throw new BusinessException("用户不存在");
        }
        if (!Objects.equals(loginUser.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(loginUser, userVO);
        String token = JWTUtil.createToken(loginUser.getId());
        // 利用redis设置token过期时间，过期时间为登录时间+过期时间
        redisCache.set(RedisConstant.REDIS_KEY_USER_LOGIN_EXPIRE + userVO.getId(), System.currentTimeMillis() + jwtProperties.getExpireTime());
        userVO.setToken(token);
        return userVO;
    }

    @Override
    public User register(UserVO user) {
        String s = (String) redisCache.get(RedisConstant.EMAIL_REGISTER_CODE + user.getEmail());
        if (!ParameterVerificationUtil.isHasText(s) || !Objects.equals(s, user.getCode())) {
            throw new BusinessException("验证码错误");
        }
        User loginUser = this.baseMapper.selectByEmail(user.getEmail());
        if (!ParameterVerificationUtil.isNull(loginUser)) {
            throw new BusinessException("用户已存在");
        }
        this.baseMapper.insert(user);
        return user;
    }
}

package cn.yahaoyang.service.impl;

import cn.yahaoyang.Mapper.UserMapper;
import cn.yahaoyang.domain.User;
import cn.yahaoyang.exception.BusinessException;
import cn.yahaoyang.service.UserService;
import cn.yahaoyang.utils.ParameterVerificationUtil;
import cn.yahaoyang.vo.RegisterUserVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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

    public final StringRedisTemplate stringRedisTemplate;

    @Override
    public User login(User user) {
        User loginUser = this.baseMapper.selectByEmail(user.getEmail());
        if (ParameterVerificationUtil.isNull(loginUser)) {
            throw new BusinessException("用户不存在");
        }
        if (!Objects.equals(loginUser.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        return loginUser;
    }

    @Override
    public User register(RegisterUserVO user) {
        String s = stringRedisTemplate.opsForValue().get(SendEmailServiceImpl.EMAIL_REGISTER_CODE + user.getEmail());
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

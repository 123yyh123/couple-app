package cn.yahaoyang.service;

import cn.yahaoyang.domain.User;
import cn.yahaoyang.vo.RegisterUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author yyh
 * @date 2024/6/7
 */
public interface UserService extends IService<User> {
    User login(User user);

    User register(RegisterUserVO user);
}

package cn.yahaoyang.service;

import cn.yahaoyang.domain.User;
import cn.yahaoyang.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author yyh
 * @date 2024/6/7
 */
public interface UserService extends IService<User> {
    UserVO login(User user);

    User register(UserVO user);
}

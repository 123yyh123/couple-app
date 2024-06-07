package cn.yahaoyang.Mapper;

import cn.yahaoyang.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where phone=#{phone}")
    User selectByPhone(String phone);

    @Select("select * from user where email=#{email}")
    User selectByEmail(String email);
}

package cn.yahaoyang.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yyh
 * @date 2024/6/7
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter

public class JwtProperties {
    // 密钥
    private String secret;
    // token过期时间
    private long expireTime;
    // 剩余多久时间刷新token
    private long refreshTime;
}
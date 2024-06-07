package cn.yahaoyang.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author yyh
 * @date 2024/6/7
 */
public class RandomUtil {
    public static String getRandomCode(int length) {
        StringBuilder code = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}

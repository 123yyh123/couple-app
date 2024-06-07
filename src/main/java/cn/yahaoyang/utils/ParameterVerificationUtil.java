package cn.yahaoyang.utils;

/**
 * @author yyh
 * @date 2024/6/7
 */
public class ParameterVerificationUtil {
    public static boolean isPhone(String phone) {
        return phone.matches("(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}");
    }

    public static boolean isHasText(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmail(String email) {
        return email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    }
}

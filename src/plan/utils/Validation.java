package plan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USER_NAME_REGEX_PATTERN = "^[a-zA-z0-9]{5,12}$";
    public static final String USER_PASSWORD_REGEX_PATTERN = "^[a-zA-z0-9@!?_#&%.^]{5,12}$";
    public static final String USER_EMAIL_REGEX_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,50}$";
    public static final String ORDER_NUMBER_REGEX_PATTERN = "^[9]\\d{6}$";
    public static final String TIME_REGEX_PATTERN = "^[0-9]{1,2}$";


    public static boolean isValidUsername(String user_name) {
        Pattern pattern = Pattern.compile(USER_NAME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(user_name);
        return matcher.find();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(USER_PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isValidEmail(String regEmail) {
        Pattern pattern = Pattern.compile(USER_EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(regEmail);
        return matcher.find();
    }

    public static boolean isValidOrderNumber(String orderNumber) {
        Pattern pattern = Pattern.compile(ORDER_NUMBER_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(orderNumber);
        return matcher.find();
    }

    public static boolean isValidTime(String time) {
        Pattern pattern = Pattern.compile(TIME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.find();
    }

}

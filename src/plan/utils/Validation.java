package plan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USER_NAME_REGEX_PATTERN = "^[a-zA-z0-9]{5,12}$";
    public static final String SURNAME_REGEX_PATTERN = "^[A-Z]+.[A-Z][a-z]{4,50}$";
    public static final String USER_PASSWORD_REGEX_PATTERN = "^[a-zA-z0-9@!?_#&%.^]{5,12}$";
    public static final String USER_EMAIL_REGEX_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{3,50}$";
    public static final String ORDER_NUMBER_REGEX_PATTERN = "^[9]\\d{6}$";
    public static final String PLANNED_TIME_REGEX_PATTERN = "^[1-9][0-9]?$";
    public static final String ACTUAL_TIME_REGEX_PATTERN = "^[0-9][0-9]?$";


    public static boolean isValidUsername(String user_name) {
        Pattern pattern = Pattern.compile(USER_NAME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(user_name);
        return matcher.find();
    }
    public static boolean isValidSurname(String name_surname) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(name_surname);
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

    public static boolean isValidPlannedTime(String time) {
        Pattern pattern = Pattern.compile(PLANNED_TIME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.find();
    }

    public static boolean isValidActualTime(String time) {
        Pattern pattern = Pattern.compile(ACTUAL_TIME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.find();
    }
}

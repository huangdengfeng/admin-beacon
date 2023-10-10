package com.seezoon.infrastructure.constants;

import java.time.format.DateTimeFormatter;

/**
 * @author dfenghuang
 * @date 2023/9/15 00:13
 */
public class Constants {

    public static final Integer SUPER_ADMIN_USER_ID = 1;

    public static final String COMMA = ",";
    public static final String UNDERLINE = "_";
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DATETIME_PATTERN);


}

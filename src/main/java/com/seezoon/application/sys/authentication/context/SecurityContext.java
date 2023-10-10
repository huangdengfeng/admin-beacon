package com.seezoon.application.sys.authentication.context;

import com.seezoon.domain.service.sys.authentication.valueobj.UserDetailsVO;
import com.seezoon.infrastructure.constants.Constants;
import java.util.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 只允许应用层使用
 */
public class SecurityContext {

    public static final Integer ANONYMOUS_USER_ID = -1;

    public static Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名也是Authenticated = true
        if (null != authentication && authentication.isAuthenticated()) {
            // 如果是匿名，返回的是个字符串
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetailsVO) {
                UserDetailsVO userDetails = (UserDetailsVO) principal;
                return userDetails.getUserId();
            }
        }
        return null;
    }

    public static boolean isSuperAdmin(Integer userId) {
        return Objects.equals(userId, Constants.SUPER_ADMIN_USER_ID);
    }

    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }

}
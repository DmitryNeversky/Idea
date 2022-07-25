package org.dneversky.gateway.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.dneversky.gateway.exception.PermissionException;
import org.dneversky.gateway.exception.UnforeseenException;
import org.dneversky.gateway.service.impl.DefaultUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class UserAdvice {

    private final DefaultUserService userService;

    public UserAdvice(DefaultUserService userService) {
        this.userService = userService;
    }

    @Pointcut("@annotation(org.dneversky.gateway.advice.annotation.PrincipalOrAdminAccess)")
    public void annotatedMethodsWithPrincipalOrAdminAccess() {}

    @Before("annotatedMethodsWithPrincipalOrAdminAccess()")
    public void beforeCurrentUserMethod(JoinPoint point) {
        Arrays.stream(point.getArgs()).forEach(e -> System.out.println(e.toString()));
//        String usernameArgument = (String) point.getArgs()[0];
//        String currentUsername = getCurrentUsername();
//        if(!usernameArgument.equals(currentUsername)) {
//            throw new PermissionException("Not enough privileges.");
//        }
    }

//    private String getCurrentUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!(authentication instanceof AnonymousAuthenticationToken)) {
//            return authentication.getName();
//        } else {
//            throw new UnforeseenException("Couldn't get username of the current user.");
//        }
//    }
}

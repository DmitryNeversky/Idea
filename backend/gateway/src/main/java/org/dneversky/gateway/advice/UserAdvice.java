package org.dneversky.gateway.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dneversky.gateway.advice.annotation.PrincipalOrAdminAccess;
import org.dneversky.gateway.exception.PermissionException;
import org.dneversky.gateway.exception.UnforeseenException;
import org.dneversky.gateway.service.impl.DefaultUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private void annotatedMethodsWithPrincipalOrAdminAccess() {}

    @After("annotatedMethodsWithPrincipalOrAdminAccess()")
    public void beforeCurrentUserMethod(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        PrincipalOrAdminAccess annotation = methodSignature.getMethod().getAnnotation(PrincipalOrAdminAccess.class);
        String targetParameterName = annotation.target();
        String[] parameterNames = methodSignature.getParameterNames();

        String targetUsername = null;
        for(int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals(targetParameterName)) {
                targetUsername = point.getArgs()[i].toString();
                break;
            }
        }
        if(targetUsername == null) {
            return;
        }

        Authentication authentication = getAuthentication();
        long foundRolesQuantity = Arrays.stream(annotation.roles())
                .filter(e -> authentication.getAuthorities().contains(new SimpleGrantedAuthority(e)))
                .count();
        if(!targetUsername.equals(authentication.getName()) && foundRolesQuantity == 0) {
            throw new PermissionException("Not enough privileges.");
        }
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication;
        } else {
            throw new UnforeseenException("Couldn't get username of the current user.");
        }
    }
}

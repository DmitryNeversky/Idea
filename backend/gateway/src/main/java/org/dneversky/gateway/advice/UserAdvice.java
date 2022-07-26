package org.dneversky.gateway.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dneversky.gateway.advice.annotation.PrincipalOrAdminAccess;
import org.dneversky.gateway.exception.PermissionException;
import org.dneversky.gateway.exception.UnforeseenException;
import org.dneversky.gateway.service.impl.DefaultUserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class UserAdvice {

    private final DefaultUserService userService;

    public UserAdvice(DefaultUserService userService) {
        this.userService = userService;
    }

    @Pointcut("@annotation(org.dneversky.gateway.advice.annotation.PrincipalOrAdminAccess)")
    private void annotatedMethodsWithPrincipalOrAdminAccess() {}

    // check if current user has the same username as incoming and certain roles

    @Before("annotatedMethodsWithPrincipalOrAdminAccess()")
    public void providePrincipalOrAdminAccess(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        PrincipalOrAdminAccess annotation = methodSignature.getMethod().getAnnotation(PrincipalOrAdminAccess.class);
        String methodUsernameParameterName = annotation.target();
        String[] parameterNames = methodSignature.getParameterNames();

        // checking parameter existing that had been specified in the annotation's
        // parameter 'target' by comparing indexes of incoming arguments and method parameters

        String targetUsername = null;
        for(int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals(methodUsernameParameterName)) {
                targetUsername = point.getArgs()[i].toString();
                break;
            }
        }
        if(targetUsername == null) {
            return;
        }
        Authentication authentication = getAuthentication();
        long foundRolesQuantity = Arrays.stream(annotation.roles())
                .filter(e -> authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet())
                        .contains(e))
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

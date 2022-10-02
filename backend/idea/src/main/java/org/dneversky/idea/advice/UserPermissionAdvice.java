package org.dneversky.idea.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dneversky.idea.advice.annotation.PrincipalOrAdminAccess;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.exception.PermissionException;
import org.dneversky.idea.exception.UnforeseenException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserPermissionAdvice {

    /** Advice's created for checking if user is admin or profile's owner by username. **/

    @Pointcut("@annotation(org.dneversky.idea.advice.annotation.PrincipalOrAdminAccess)")
    private void annotatedMethodsWithPrincipalOrAdminAccess() {}

    // check if current user has the same username as incoming and certain roles

    @Before("annotatedMethodsWithPrincipalOrAdminAccess()")
    public void providePrincipalOrAdminAccess(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        PrincipalOrAdminAccess annotation = methodSignature.getMethod().getAnnotation(PrincipalOrAdminAccess.class);
        String methodUsernameParameterName = annotation.target();
        String[] parameterNames = methodSignature.getParameterNames();

        // checking for parameter existing that had been specified in the annotation's
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
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()));
        if(!targetUsername.equals(authentication.getName()) && !isAdmin) {
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

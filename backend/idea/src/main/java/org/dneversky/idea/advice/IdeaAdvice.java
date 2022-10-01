package org.dneversky.idea.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dneversky.idea.advice.annotation.AuthorOrAdminAccess;
import org.dneversky.idea.entity.Idea;
import org.dneversky.idea.entity.Role;
import org.dneversky.idea.exception.EntityNotFoundException;
import org.dneversky.idea.exception.PermissionException;
import org.dneversky.idea.exception.UnforeseenException;
import org.dneversky.idea.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IdeaAdvice {

    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaAdvice(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    /** Advice's created for checking if user is author of a given Idea or user is admin. **/

    @Pointcut("@annotation(org.dneversky.idea.advice.annotation.AuthorOrAdminAccess)")
    private void annotatedMethodsWithAuthorOrAdminAccess() {}

    @Before("annotatedMethodsWithAuthorOrAdminAccess()")
    public void provideAuthorOrAdminAccess(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        AuthorOrAdminAccess annotation = methodSignature.getMethod().getAnnotation(AuthorOrAdminAccess.class);
        String methodIdeaIdParameterName = annotation.target();
        String[] parameterNames = methodSignature.getParameterNames();

        // checking for parameter existing that had been specified in the annotation's
        // parameter 'target' by comparing indexes of incoming arguments and method parameters

        long ideaId = 0;
        for(int i = 0; i < parameterNames.length; i++) {
            if(parameterNames[i].equals(methodIdeaIdParameterName)) {
                ideaId = (long) point.getArgs()[i];
                break;
            }
        }
        if(ideaId == 0) {
            return;
        }
        long finalTargetId = ideaId;
        Idea idea = ideaRepository.findById(ideaId).orElseThrow(
                () -> new EntityNotFoundException("Idea with id " + finalTargetId + " not found."));
        Authentication authentication = getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.getName()));
        if(!idea.getAuthor().getUsername().equals(authentication.getName()) && !isAdmin) {
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

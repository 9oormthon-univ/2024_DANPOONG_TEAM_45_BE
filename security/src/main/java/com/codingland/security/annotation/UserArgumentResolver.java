package com.codingland.security.annotation;

import com.codingland.common.exception.security.SecurityCustomException;
import com.codingland.common.exception.security.SecurityErrorCode;
import com.codingland.domain.user.entity.User;
import com.codingland.domain.user.service.UserQueryService;
import com.codingland.security.jwt.dto.UserInfoDTO;
import com.codingland.security.oauth.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserQueryService userQueryService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(UserResolver.class);
        boolean isOrganizationParameterType = parameter.getParameterType().isAssignableFrom(User.class);
        return hasParameterAnnotation && isOrganizationParameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            String name = ((UserInfoDTO) principal).name();
            System.out.println("name ::: " + name);
            return userQueryService.findByUserName(name);
        } catch (ClassCastException e) {
            // 로그아웃된 토큰
            throw new SecurityCustomException(SecurityErrorCode.UNAUTHORIZED);
        }
    }
}

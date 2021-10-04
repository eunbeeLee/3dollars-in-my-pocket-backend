package com.depromeet.threedollar.api.config.resolver;

import com.depromeet.threedollar.api.config.interceptor.Auth;
import com.depromeet.threedollar.common.exception.model.InternalServerException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(UserId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(Long.class);
        if (hasAnnotation && parameter.getMethodAnnotation(Auth.class) == null) {
            throw new InternalServerException("인증이 필요한 컨트롤러 입니다. Auth 어노테이션을 붙여주세요.");
        }
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return webRequest.getAttribute("userId", 0);
    }

}

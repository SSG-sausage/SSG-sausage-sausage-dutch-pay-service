package com.ssg.sausagecartsharecalculationapi.common.config.resolver;

import javax.validation.constraints.NotNull;

import com.ssg.sausagecartsharecalculationapi.common.exception.InternalServerException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MbrIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MbrId.class) && Long.class.equals(
                parameter.getParameterType());
    }

    @NotNull
    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        String mbrId = webRequest.getHeader("mbrId");
        if (mbrId == null) {
            throw new InternalServerException(
                    String.format("mbrId 를 가져오지 못했습니다. (%s - %s)", parameter.getClass(),
                            parameter.getMethod()));
        }
        return Long.valueOf(mbrId);
    }
}
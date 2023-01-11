package com.ssg.sausagedutchpayapi.common.entity;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class MbrAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String mbrId = request.getHeader("mbrId");
        if (mbrId == null) {
            return Optional.empty();
        }
        return Optional.of(Long.valueOf(mbrId));
    }
}
package ch.uzh.ifi.imrg.platform.security;

import ch.uzh.ifi.imrg.platform.service.TherapistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TherapistIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final TherapistService therapistService;

    public TherapistIdArgumentResolver(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(CurrentTherapistId.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NonNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        if (httpServletRequest == null) {
            throw new IllegalStateException("Could not get HttpServletRequest from NativeWebRequest");
        }

        return therapistService.getTherapistIdBasedOnRequest(httpServletRequest);
    }
}
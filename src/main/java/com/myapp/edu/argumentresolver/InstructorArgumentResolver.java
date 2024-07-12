package com.myapp.edu.argumentresolver;

import com.myapp.edu.annotation.Instructor;
import com.myapp.edu.common.MemberConst;
import com.myapp.edu.enums.Role;
import com.myapp.edu.dto.member.MemberSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
@Slf4j
public class InstructorArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasInstructorAnnotation = parameter.hasParameterAnnotation(Instructor.class);
        boolean hassMemberSessionType = MemberSession.class.isAssignableFrom(parameter.getParameterType());
        return hasInstructorAnnotation && hassMemberSessionType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(MemberConst.LOGIN_MEMBER) == null) {
            return null;
        }
        MemberSession memberSession = (MemberSession) session.getAttribute(MemberConst.LOGIN_MEMBER);
        return memberSession.getRole() == Role.INSTRUCTOR ? memberSession : null;
    }
}

package com.myapp.edu.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.edu.common.MemberConst;
import com.myapp.edu.dto.rest.RestResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(MemberConst.LOGIN_MEMBER) == null) {
            sendUnauthorizedError(response, "인증되지 않은 사용자입니다");
            return false;
        }
        return true;
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) throws Exception {
        RestResponse<String> restResponse = new RestResponse<>(message, HttpServletResponse.SC_UNAUTHORIZED);
        String jsonResponse = objectMapper.writeValueAsString(restResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
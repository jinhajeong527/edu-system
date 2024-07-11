package com.myapp.edu.auditor;

import com.myapp.edu.common.MemberConst;
import com.myapp.edu.dto.member.MemberSession;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final HttpServletRequest request;

    @Override
    public Optional<String> getCurrentAuditor() {
        MemberSession session = (MemberSession) request.getSession().getAttribute(MemberConst.LOGIN_MEMBER);
        return Optional.ofNullable(session != null ? session.getEmail() : null);
    }
}

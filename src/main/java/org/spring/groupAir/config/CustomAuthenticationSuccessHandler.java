package org.spring.groupAir.config;

import org.spring.groupAir.member.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String targetUrl = "/";

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                targetUrl = "/role/admin";
                break;
            } else if (authority.getAuthority().equals("ROLE_MANAGER")) {
                targetUrl = "/role/manager";
                break;
            } else if (authority.getAuthority().equals("ROLE_MEMBER")) {
                targetUrl = "/role/member";
                break;
            }
        }

        // html로 변환
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script> " +
                " alert('" + authentication.getName() + "님 반갑습니다.') ; " +
                "location.href='" + targetUrl + "'; " +
                " </script>");

        out.close();
    }
}




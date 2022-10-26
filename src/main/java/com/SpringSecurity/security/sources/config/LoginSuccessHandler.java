package com.SpringSecurity.security.sources.config;

import com.SpringSecurity.security.sources.userDetails.MyUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**Создаем Handler, управляющий перенаправлением после успешной аутентификации*/
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String redirectUrl = request.getContextPath();
        if (myUserDetails.hasRole("ADMIN")) {
            redirectUrl += "admin";
        } else {
            redirectUrl += "user";
        }
        response.sendRedirect(redirectUrl);
    }

}

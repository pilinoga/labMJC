package com.epam.esm.module3.controller.exception.handler;

import com.epam.esm.module3.controller.exception.ErrorData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint{
    public static final String ENCODING = "UTF-8";
    public static final String MESSAGE = "authenticationException";
    public static final Integer ERROR_CODE = 30006;
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationEntryPointImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING);

        String message = messageSource.getMessage(MESSAGE, new Object[]{}, request.getLocale());
        response.getWriter()
                .write(String.valueOf(new ObjectMapper()
                        .writeValueAsString(new ErrorData(message,ERROR_CODE))));
    }
}

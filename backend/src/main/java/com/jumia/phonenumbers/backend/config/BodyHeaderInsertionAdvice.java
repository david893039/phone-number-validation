package com.jumia.phonenumbers.backend.config;

import com.jumia.phonenumbers.backend.controller.CustomerController;
import com.jumia.phonenumbers.backend.controller.dto.Resource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static com.jumia.phonenumbers.backend.controller.dto.BodyHeader.header;
import static com.jumia.phonenumbers.backend.utils.Constants.SUCCESS_CODE;
import static com.jumia.phonenumbers.backend.utils.Constants.SUCCESS_RESPONSE_MESSAGE;

@RestControllerAdvice
public class BodyHeaderInsertionAdvice implements ResponseBodyAdvice<Resource> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return methodParameter.getContainingClass() == CustomerController.class;
    }

    @Override
    public Resource beforeBodyWrite(Resource resource,
                                    MethodParameter methodParameter, MediaType mediaType,
                                    Class<? extends HttpMessageConverter<?>> aClass,
                                    ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (resource != null && resource.getResponseCode() != null)
            resource.setHeader(header(resource.getResponseCode(), SUCCESS_RESPONSE_MESSAGE));
        else if (resource != null)
            resource.setHeader(header(SUCCESS_CODE, SUCCESS_RESPONSE_MESSAGE));

        return resource;
    }
}

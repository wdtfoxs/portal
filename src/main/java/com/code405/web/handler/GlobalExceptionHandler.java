package com.code405.web.handler;

import com.code405.constants.Routes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by birth on 01.02.2017.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public RedirectView pageNotFound() {
        return new RedirectView(Routes.NOT_FOUND_URI);
    }

//    @ExceptionHandler({MissingServletRequestParameterException.class})
//    public RedirectView missingParameter() {
//        return new RedirectView(Routes.ROOT_URI);
//    }

}

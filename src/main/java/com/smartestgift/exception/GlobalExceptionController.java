package com.smartestgift.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by dikkini on 20/06/14.
 */

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllException(Exception ex) {
        return new ResponseEntity<String>(HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(InternalErrorException.class)
    public @ResponseBody ResponseEntity handleInternalErrorException(HttpServletResponse response) {
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
}

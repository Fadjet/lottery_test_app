package com.apria.lottery.controller;

import com.apria.lottery.exception.EntityNotFoundException;
import com.apria.lottery.exception.EntityValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(EntityValidationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleInvalidRequestError(EntityValidationException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleInvalidRequestError(EntityNotFoundException ex) {
    return ex.getMessage();
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public String handleUnexpectedServerError(RuntimeException ex) {
    return ex.getMessage();
  }
}


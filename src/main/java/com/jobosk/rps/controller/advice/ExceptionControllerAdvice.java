package com.jobosk.rps.controller.advice;

import com.jobosk.rps.exception.handler.ControlledExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionControllerAdvice extends ControlledExceptionHandler {

}
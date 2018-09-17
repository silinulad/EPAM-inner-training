package com.example.silin.guitarshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(Throwable.class)
	public ModelAndView handleError(HttpServletRequest request, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", ex.getMessage());
		modelAndView.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
		modelAndView.addObject("url", request.getRequestURL());
		modelAndView.setViewName("error-page");
		return modelAndView;
	}

}

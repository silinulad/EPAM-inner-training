package com.example.silin.guitarshop.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		LOGGER.debug("PreHaahdle::Request URI: " + request.getRequestURI());
		LOGGER.debug("PreHaahdle::Request method: " + request.getMethod());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.debug("PostHaahdle::Request URI: " + request.getRequestURI());
		LOGGER.debug("PostHaahdle::Request Content-Type: " + request.getContentType());
		LOGGER.debug("PostHaahdle::Request method: " + request.getMethod());
		LOGGER.debug("PostHaahdle::Request character encoding: " + request.getCharacterEncoding());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LOGGER.debug("afterCompletion::Response status: " + response.getStatus());
		LOGGER.debug("afterCompletion::Response character encoding: " + response.getCharacterEncoding());
		LOGGER.debug("afterCompletion::Request method: " + request.getMethod());
	}

}

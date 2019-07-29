package com.sample.WireMockSpringIntegration.configuration;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestResponseLogging extends AbstractRequestLoggingFilter {
	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.debug("Before , " + message);
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.debug("After ,, " + message);
	}
}

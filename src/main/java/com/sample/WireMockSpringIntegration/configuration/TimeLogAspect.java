package com.sample.WireMockSpringIntegration.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.apache.commons.beanutils.PropertyUtils;

@Aspect
@Component
@Slf4j
public class TimeLogAspect {

	@Around("@annotation(com.sample.WireMockSpringIntegration.configuration.TimedLog) && args(.., @RequestBody requestBody)")
	public Object timeLogAspect(final ProceedingJoinPoint joinPoint, Object requestBody) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		TimedLog timed = signature.getMethod().getAnnotation(TimedLog.class);

		String methodName = joinPoint.getSignature().getName();
		String[] reqVars = timed.requestAttributes();
		StringBuilder sb = new StringBuilder();
		sb.append("Before Execution :: ").append(methodName).append(" : ");
		for(String field: reqVars) {
			sb.append(field).append("=")
					.append(PropertyUtils.getProperty(requestBody, field)).append(",");
		}
		log.info(sb.toString());
		long start = System.currentTimeMillis();

		Object value = null;
		try {
			value = joinPoint.proceed();
		} finally {
			long duration = System.currentTimeMillis() - start;
			String[] resVars = timed.responseAttributes();
			Object resBody = PropertyUtils.getProperty(value, "body");
			StringBuilder resBuilder = new StringBuilder();
			resBuilder.append("After Execution :: ").append(methodName).append(" : ");
			for(String field: resVars) {
				Object prop;
				try {
					prop = PropertyUtils.getProperty(resBody, field);
				} catch (NoSuchMethodException e) {
					prop = PropertyUtils.getProperty(requestBody, field);
				}
				resBuilder.append(field).append("=")
						.append(prop).append(",");
			}
			resBuilder.append("duration: ").append(duration).append("ms");
			log.info(resBuilder.toString());
		}
		return value;
	}
}

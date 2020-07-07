package com.navent.realestate.trace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Aspect
public class TracingAspect {

	private Tracer tracer;

	public TracingAspect(Tracer tracer) {
		this.tracer = tracer;
	}

	@Around("@annotation(com.navent.realestate.trace.Traced)")
	public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
		String class_name = jp.getTarget().getClass().getName();
		String method_name = jp.getSignature().getName();

		Span span = tracer
				.buildSpan(class_name + "." + method_name)
				.withTag("class", class_name)
				.withTag("method", method_name)
				.start();
		Object result = null;
		try (Scope scope = tracer.activateSpan(span)) {
			result = jp.proceed();
		} catch (Exception e) {
			span.log(e.getMessage());
			throw e;
		} finally {
			span.finish();
		}
		
		return result;
	}
}

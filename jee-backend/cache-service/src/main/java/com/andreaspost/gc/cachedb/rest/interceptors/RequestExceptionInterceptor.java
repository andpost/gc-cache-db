package com.andreaspost.gc.cachedb.rest.interceptors;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Interceptor for some basic handling of request exceptions.
 * 
 * @author Andreas Post
 */
public class RequestExceptionInterceptor {

	private static final Logger LOG = Logger.getLogger(RequestExceptionInterceptor.class.getName());

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		try {
			return context.proceed();
		} catch (ClientErrorException e) {
			LOG.log(Level.INFO, "ClientErrorException: " + e.getMessage(), e);
			return e.getResponse();
		} catch (ServerErrorException e) {
			LOG.log(Level.SEVERE, "ServerErrorException: " + e.getMessage(), e);
			return e.getResponse();
		} catch (WebApplicationException e) {
			LOG.log(Level.SEVERE, "WebApplicationException: " + e.getMessage(), e);
			return e.getResponse();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
			return Response.serverError().build();
		}
	}
}

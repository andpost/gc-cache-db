package com.andreaspost.gc.cachedb.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.andreaspost.gc.cachedb.rest.resource.Log;

/**
 * Resource controller for geo cache logs.
 * 
 * @author Andreas Post
 */
@Path(LogsResourceController.RESOURCE_PATH)
public class LogsResourceController extends AbstractResourceController<Log> {

	public static final String RESOURCE_PATH = GeoCacheResourceController.RESOURCE_PATH + "{gccode}/logs/";

	@PathParam("gccode")
	private String gcCode;

	// TODO GET logs/id & logs/

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listLogs() {
		Set<Log> logsList = getGeoCacheService().listLogs(gcCode);

		if (logsList == null) {
			return Response.status(Status.NOT_FOUND).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		logsList.forEach(log -> addResourceURL(log));

		return Response.ok(logsList).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	/**
	 * Adds an answer to a given question.
	 * 
	 * @param answer
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLog(Log log) {

		if (!getGeoCacheService().addLog(gcCode, log)) {
			return Response.status(Status.NOT_FOUND).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		addResourceURL(log);

		URI location;

		try {
			location = new URI(log.getHref());
		} catch (URISyntaxException e) {
			return Response.serverError().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		return Response.created(location).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@Override
	String getResourcePath() {
		return RESOURCE_PATH.replace("{gccode}", gcCode);
	}
}

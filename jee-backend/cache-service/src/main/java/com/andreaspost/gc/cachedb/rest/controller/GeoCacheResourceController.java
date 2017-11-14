package com.andreaspost.gc.cachedb.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.andreaspost.gc.cachedb.interceptors.MethodLoggingInterceptor;
import com.andreaspost.gc.cachedb.persistence.exception.DuplicateGeoCacheException;
import com.andreaspost.gc.cachedb.rest.interceptors.RequestExceptionInterceptor;
import com.andreaspost.gc.cachedb.rest.interceptors.RequestLoggingInterceptor;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.service.GeoCacheService;

/**
 * Resource controller for {@link GeoCache} resource.
 * 
 * @author Andreas Post
 */
@Path(GeoCacheResourceController.GEOCACHE_RESOURCE_PATH)
@Interceptors({ RequestLoggingInterceptor.class, MethodLoggingInterceptor.class, RequestExceptionInterceptor.class })
public class GeoCacheResourceController {

	public static final String GEOCACHE_RESOURCE_PATH = "rest/caches/";

	private static final Logger LOG = Logger.getLogger(GeoCacheResourceController.class.getName());

	private static final String EXPAND_LOGS = "logs";

	@Context
	protected UriInfo uriInfo;

	@Inject
	GeoCacheService geoCacheService;

	/**
	 * Retrieves a geocache resource by gc code.
	 * 
	 * @param gcCode
	 * @param expand
	 * @return
	 */
	@GET
	@Path("{gccode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCache(@PathParam("gccode") String gcCode, @QueryParam("expand") String expand) {

		GeoCache geoCache = geoCacheService.getGeoCacheByGcCode(gcCode, EXPAND_LOGS.equalsIgnoreCase(expand));

		if (geoCache == null) {
			return Response.status(Status.NOT_FOUND).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		addResourceURL(geoCache);

		return Response.ok(geoCache).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	/**
	 * Lists geocaches by querying with latitude / longitude / radius.
	 * 
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return list of geocaches within radius from latitude / longitude
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGeoCaches(@QueryParam("lat") double latitude, @QueryParam("lon") double longitude,
			@QueryParam("radius") int radius, @QueryParam("expand") String expand) {

		List<GeoCache> cacheList = geoCacheService.listGeoCaches(latitude, longitude, radius,
				EXPAND_LOGS.equalsIgnoreCase(expand));

		cacheList.stream().forEach(c -> addResourceURL(c));

		return Response.ok(cacheList).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGeoCache(GeoCache geoCache) {

		GeoCache result;

		try {
			result = geoCacheService.createGeoCache(geoCache);
			addResourceURL(result);
		} catch (DuplicateGeoCacheException e) {
			return Response.status(Status.CONFLICT).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		URI location;

		try {
			location = new URI(result.getHref());
		} catch (URISyntaxException e) {
			return Response.serverError().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
		}

		return Response.created(location).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOrUpdateGeoCache(GeoCache geoCache) {

		// LOG.info(geoCache.toString());

		GeoCache result = geoCacheService.createOrUpdateGeoCache(geoCache);

		return Response.ok().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	private void addResourceURL(GeoCache geoCache) {
		geoCache.setHref(uriInfo.getBaseUri().toString() + GEOCACHE_RESOURCE_PATH + geoCache.getGcCode());
	}

}

package com.andreaspost.gc.cachedb.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.andreaspost.gc.cachedb.interceptors.MethodLoggingInterceptor;
import com.andreaspost.gc.cachedb.persistence.exception.DuplicateGeoCacheException;
import com.andreaspost.gc.cachedb.rest.interceptors.RequestExceptionInterceptor;
import com.andreaspost.gc.cachedb.rest.interceptors.RequestLoggingInterceptor;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;

/**
 * Resource controller for {@link GeoCache} resource.
 * 
 * @author Andreas Post
 */
@Path(GeoCacheResourceController.RESOURCE_PATH)
@Interceptors({ RequestLoggingInterceptor.class, MethodLoggingInterceptor.class, RequestExceptionInterceptor.class })
public class GeoCacheResourceController extends AbstractResourceController<GeoCache> {

	public static final String RESOURCE_PATH = "caches/";

	private static final Logger LOG = Logger.getLogger(GeoCacheResourceController.class.getName());

	private static final String EXPAND_DETAILS = "details";

	private static final String EXPAND_LOGS = "logs";

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
		boolean expandDetails = hasExpandParamDetails(expand, EXPAND_DETAILS);

		GeoCache geoCache = getGeoCacheService().getGeoCacheByGcCode(gcCode, expandDetails);

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
		boolean expandDetails = hasExpandParamDetails(expand, EXPAND_DETAILS);

		List<GeoCache> cacheList = getGeoCacheService().listGeoCaches(latitude, longitude, radius,
				expandDetails);

		cacheList.stream().forEach(c -> addResourceURL(c));

		return Response.ok(cacheList).header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGeoCache(GeoCache geoCache) {

		GeoCache result;

		try {
			result = getGeoCacheService().createGeoCache(geoCache);
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

		GeoCache result = getGeoCacheService().createOrUpdateGeoCache(geoCache);

		// TODO if new entry then add location header?

		return Response.ok().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@DELETE
	@Path("{gccode}")
	public Response deleteGeoCache(@PathParam("gccode") String gcCode) {

		getGeoCacheService().deleteGeoCacheByGcCode(gcCode);

		return Response.noContent().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8).build();
	}

	@Override
	String getResourcePath() {
		return RESOURCE_PATH;
	}

	/**
	 * Returns {@link GeoCache#getGcCode()} as id of this resource.
	 */
	@Override
	protected String getResourceId(GeoCache resource) {
		return resource.getGcCode();
	}

	/**
	 * Returns true if the queryParam contains the expandOption.
	 * 
	 * The queryParam must be a single option or comma separated list of options.
	 * 
	 * @param queryParam
	 * @param expandOption
	 * @return
	 */
	private boolean hasExpandParamDetails(final String queryParam, final String expandOption) {
		if (queryParam != null) {
			String[] expandParts = queryParam.split(",");

			for (String expPart : expandParts) {
				if (expandOption.equalsIgnoreCase(expPart.trim())) {
					return true;
				}
			}
		}
		return false;
	}
}

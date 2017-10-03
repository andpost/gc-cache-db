package com.andreaspost.gc.cachedb.rest.resource;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.andreaspost.gc.cachedb.rest.Constants;
import com.andreaspost.gc.cachedb.service.GeoCacheService;

@Path(Constants.CACHE_RESOURCE_PATH)
public class GeoCacheResourceController {

	private static final Logger LOG = Logger.getLogger(GeoCacheResourceController.class.getName());

	private static final String EXPAND_DETAILS = "details";

	private static GeoCache cache = new GeoCache("123");

	@Context
	protected UriInfo uriInfo;

	@Inject
	GeoCacheService geoCacheService;

	@GET
	@Path("{gccode}")
	@Produces(Constants.MEDIA_TYPE_JSON)
	public Response getCache(@PathParam("gccode") String gcCode, @QueryParam("expand") String expand) {

		GeoCache geoCache = geoCacheService.getGeoCacheByGcCode(gcCode, Boolean.valueOf(expand));

		if (geoCache == null) {
			return Response.status(Status.NOT_FOUND).header(Constants.CONTENT_ENC_KEY, Constants.CHARSET_UTF8).build();
		}

		return Response.ok(geoCache).header(Constants.CONTENT_ENC_KEY, Constants.CHARSET_UTF8).build();
	}

	@PUT
	@Consumes(Constants.MEDIA_TYPE_JSON)
	public Response createOrUpdateGeoCache(GeoCache geoCache) {

		// LOG.info(geoCache.toString());

		GeoCache result = geoCacheService.createGeoCache(geoCache);

		return Response.ok().header(Constants.CONTENT_ENC_KEY, Constants.CHARSET_UTF8).build();
	}

}

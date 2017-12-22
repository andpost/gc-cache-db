package com.andreaspost.gc.cachedb.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.andreaspost.gc.cachedb.TestsBase;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;

/**
 * REST API Tests for {@link GeoCache} resource.
 * 
 * @author Andreas Post
 */
public class GeoCacheResourceControllerIT extends TestsBase {

	private static final Logger LOG = Logger.getLogger(GeoCacheResourceControllerIT.class.getName());

	@Test
	public void crudTest() {
		GeoCache cache = getDummyGeoCache();

		// 1. Create
		Response response = given().headers(headers).contentType(CONTENT_TYPE).body(cache).expect()
				.log().all().post("caches");

		response.then().assertThat().statusCode(Status.CREATED.getStatusCode());

		String location = response.getHeader("location");

		assertNotNull("Location header must not be null", location);
		assertTrue("Location header must include resource path.", location.contains("caches/"));

		// 2. Read
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().get(location);

		response.then().assertThat().statusCode(Status.OK.getStatusCode());

		GeoCache responseCache = null;

		try {
			responseCache = new ObjectMapper().readValue(response.body().asString(), new TypeReference<GeoCache>() {
			});
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unable to parse resource response.", e);
			fail(e.getMessage());
		}

		// TODO more asserts
		assertEquals("GC Code must be the same.", GC_CODE, responseCache.getGcCode());

		// 3. Delete
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().log().all().delete(location);

		response.then().assertThat().statusCode(Status.NO_CONTENT.getStatusCode());

		// 4. at least check the deletion
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().log().all().get(location);

		response.then().assertThat().statusCode(Status.NOT_FOUND.getStatusCode());
	}
}

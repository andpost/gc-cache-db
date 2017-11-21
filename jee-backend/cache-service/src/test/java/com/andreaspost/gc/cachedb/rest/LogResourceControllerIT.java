package com.andreaspost.gc.cachedb.rest;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.andreaspost.gc.cachedb.TestsBase;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.Log;
import com.andreaspost.gc.cachedb.rest.resource.LogType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;

public class LogResourceControllerIT extends TestsBase {

	private static final Logger LOG = Logger.getLogger(LogResourceControllerIT.class.getName());

	@Test
	public void addLogTest() {
		GeoCache cache = getDummyGeoCache();

		// 1. Create cache
		Response response = given().headers(headers).contentType(CONTENT_TYPE).body(cache).expect()
				.log().all().post("caches");

		response.then().assertThat().statusCode(Status.CREATED.getStatusCode());

		String location = response.getHeader("location");

		assertNotNull("Location header must not be null", location);
		assertTrue("Location header must include resource path.", location.contains("caches/"));

		Log log = createDummyLog();

		// 2. Add a log
		response = given().headers(headers).contentType(CONTENT_TYPE).body(log).expect()
				.log().all().post(location + "/logs");

		response.then().assertThat().statusCode(Status.CREATED.getStatusCode());

		log.setId("987654");
		log.setType(LogType.NOTE);

		// 3. Add a second log
		response = given().headers(headers).contentType(CONTENT_TYPE).body(log).expect()
				.log().all().post(location + "/logs");

		response.then().assertThat().statusCode(Status.CREATED.getStatusCode());

		// 4. List logs
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().log().all().get(location + "/logs");

		response.then().assertThat().statusCode(Status.OK.getStatusCode());

		List<Log> logsList = null;

		try {
			logsList = new ObjectMapper().readValue(response.body().asString(), new TypeReference<List<Log>>() {
			});
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unable to parse resource response.", e);
			fail(e.getMessage());
		}

		assertNotNull("Logs list resource must not be null.", logsList);
		assertTrue("Logs list size is expected to be 2, but actually is: " + logsList.size(), logsList.size() == 2);

		// 5. Delete
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().log().all().delete(location);

		response.then().assertThat().statusCode(Status.NO_CONTENT.getStatusCode());

		// 6. at least check the deletion
		response = given().headers(headers).contentType(CONTENT_TYPE).expect().log().all().get(location);

		response.then().assertThat().statusCode(Status.NOT_FOUND.getStatusCode());

		// TODO check deletion of log
	}

	private Log createDummyLog() {
		Log log = new Log();

		log.setId("123456");
		log.setDate(LocalDateTime.now());
		log.setType(LogType.FOUND_IT);

		return log;
	}

}

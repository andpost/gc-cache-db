package com.andreaspost.gc.cachedb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geojson.Point;
import org.junit.Before;

import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.ContainerType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.GeoCacheDetails;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Headers;

/**
 * Abstract base class for several tests.
 * 
 * @author Andreas Post
 */
public abstract class TestsBase {

	private static final Logger LOG = Logger.getLogger(TestsBase.class.getName());

	protected static final String CONTENT_TYPE = "application/json; charset=UTF-8";

	protected static final String BASE_PATH = "cachedb/";

	protected static final String GC_CODE = "GC1234";
	protected static final Point COORDS = new Point(51.1234, 13.1234);

	protected Headers headers;

	@Before
	public void init() throws MalformedURLException {
		List<Header> headerList = new ArrayList<Header>();
		headerList = new ArrayList<Header>();
		headerList.add(new Header("Accept-Language", "de"));

		headers = new Headers(headerList);

		Properties props = loadProperties("test-config.properties");
		String hostProperty = (String) props.get("test.hostname");
		String portProperty = (String) props.get("test.port");

		URL baseURL = new URL("http://" + hostProperty + ":" + portProperty + "/" + BASE_PATH);

		RestAssured.baseURI = String.format("%s://%s:%s", baseURL.getProtocol(), baseURL.getHost(), baseURL.getPort());
		RestAssured.port = baseURL.getPort();
		RestAssured.basePath = BASE_PATH;
		RestAssured.config = RestAssured.config()
				.connectionConfig(new ConnectionConfig().closeIdleConnectionsAfterEachResponse());
	}

	/**
	 * Load props for test.
	 * 
	 * @param propertyRessource
	 * @return
	 */
	protected Properties loadProperties(final String propertyRessource) {

		if (propertyRessource == null) {
			throw new IllegalArgumentException("Missing propertyRessource name.");
		}

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		Properties result = null;
		InputStream is = null;

		try {
			is = classLoader.getResourceAsStream(propertyRessource);

			if (is != null) {
				result = new Properties();
				result.load(is);
			} else {
				throw new IllegalArgumentException("File not found: " + propertyRessource);
			}
		} catch (IOException e) {
			result = null;
			throw new IllegalStateException("Error accessing file: " + propertyRessource);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, "Could not close stream", e);
				}
			}
		}
		return result;
	}

	/**
	 * Create a dummy cache for testing.
	 * 
	 * TODO add more fields
	 * 
	 * @return
	 */
	protected GeoCache getDummyGeoCache() {
		GeoCache cache = new GeoCache(GC_CODE);
		cache.setPlacedAt(LocalDateTime.of(2017, 01, 01, 12, 12));

		cache.setCoordinates(COORDS);
		cache.setType(CacheType.MYSTERY);
		cache.setContainer(ContainerType.LARGE);

		GeoCacheDetails details = new GeoCacheDetails();
		details.setFavPoints(Integer.valueOf(99));
		cache.setDetails(details);

		return cache;
	}
}

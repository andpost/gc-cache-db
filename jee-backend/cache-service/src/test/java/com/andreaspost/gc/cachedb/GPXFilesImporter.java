package com.andreaspost.gc.cachedb;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.fail;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.geojson.Point;
import org.junit.Test;

import com.andreaspost.gc.cachedb.rest.resource.Attribute;
import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.Log;
import com.andreaspost.gc.cachedb.rest.resource.LogType;
import com.andreaspost.gc.cachedb.rest.resource.User;
import com.groundspeak.cache._1._0._1.Cache;
import com.groundspeak.cache._1._0._1.Cache.Logs.Log.Finder;
import com.groundspeak.cache._1._0._1.Cache.Owner;
import com.jayway.restassured.response.Response;
import com.topografix.gpx._1._0.Gpx;
import com.topografix.gpx._1._0.Gpx.Wpt;

/**
 * Test class used for importing geocaching.com GPX files and importing them
 * using the REST API.
 * 
 * @author Andreas Post
 */
public class GPXFilesImporter extends TestsBase {

	private static final Logger LOG = Logger.getLogger(GPXFilesImporter.class.getName());

	@Test
	public void importData() throws InterruptedException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		try {
			File folder = new File(classLoader.getResource("").toURI());
			File[] listFiles = folder.listFiles((f, name) -> name.toLowerCase().endsWith(".gpx"));

			for (File file : listFiles) {
				// activate this line to start importing data
				// doImport(file);
			}
		} catch (Exception e) {
			fail("Error accessing resources directory: " + e.getMessage());
		}
	}

	/**
	 * Import all waypoints from the given gpx file.
	 * 
	 * @param file
	 * @param category
	 */
	@SuppressWarnings("rawtypes")
	private void doImport(File file) {
		System.out.println("Start importing " + file.getName());

		try {
			JAXBContext jc10 = JAXBContext.newInstance(Gpx.class, Cache.class);
			Unmarshaller unmarshaller = jc10.createUnmarshaller();

			Gpx gpx = (Gpx) unmarshaller.unmarshal(file);
			List<Wpt> wptList = gpx.getWpt();

			for (Wpt wpt : wptList) {
				List<Object> any = wpt.getAny();
				for (Object object : any) {
					Cache cache = (Cache) object;

					GeoCache geoCache = new GeoCache();
					geoCache.setGcCode(wpt.getName());
					geoCache.setId(cache.getId());
					geoCache.setName(cache.getName());
					geoCache.setCoordinates(new Point(wpt.getLon().doubleValue(), wpt.getLat().doubleValue()));
					geoCache.setType(CacheType.of(cache.getType()));

					if (wpt.getTime() != null) {
						geoCache.setPlacedAt(wpt.getTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
					}

					geoCache.setPlacedBy(cache.getPlacedBy());
					geoCache.setContainer(cache.getContainer());
					geoCache.setDifficulty(Float.parseFloat(cache.getDifficulty()));
					geoCache.setTerrain(Float.parseFloat(cache.getTerrain()));
					geoCache.setCountry(cache.getCountry());
					geoCache.setState(cache.getState());

					Owner owner = cache.getOwner().get(0);
					geoCache.setOwner(new User(owner.getValue(), owner.getId()));

					geoCache.setShortDescription(cache.getShortDescription().get(0).getValue());
					geoCache.setLongDescription(cache.getLongDescription().get(0).getValue());

					for (Cache.Attributes attrs : cache.getAttributes()) {
						for (Cache.Attributes.Attribute attr : attrs.getAttribute()) {
							geoCache.getAttributes().add(new Attribute(attr.getValue(), attr.getId()));
						}
					}

					for (Cache.Logs logs : cache.getLogs()) {
						for (Cache.Logs.Log log : logs.getLog()) {
							Finder finder = log.getFinder().get(0);
							Instant instant = Instant.parse(log.getDate());
							LocalDateTime dt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin"));

							geoCache.getLogs()
									.add(new Log(dt, LogType.of(log.getType()),
											new User(finder.getValue(), finder.getId()),
											log.getText().get(0).getValue(), log.getId()));
						}
					}

					Response response = given().headers(headers).contentType(CONTENT_TYPE).body(geoCache).expect()
							.put("cache");

					response.then().assertThat().statusCode(Status.OK.getStatusCode());

				}
				// break;
			}
			// Thread.sleep(5);

			System.out.println("Imported " + wptList.size() + " Geocaches from " + file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Error accessing file: " + file, e);
		}
	}
}

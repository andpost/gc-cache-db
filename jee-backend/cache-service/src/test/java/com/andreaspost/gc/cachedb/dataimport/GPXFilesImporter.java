package com.andreaspost.gc.cachedb.dataimport;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.fail;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.geojson.Point;
import org.junit.Test;

import com.andreaspost.gc.cachedb.TestsBase;
import com.andreaspost.gc.cachedb.rest.resource.Attribute;
import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.ContainerType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.GeoCacheDetails;
import com.andreaspost.gc.cachedb.rest.resource.Log;
import com.andreaspost.gc.cachedb.rest.resource.LogType;
import com.andreaspost.gc.cachedb.rest.resource.User;
import com.groundspeak.cache._1._0._1.Cache;
import com.groundspeak.cache._1._0._1.Cache.Logs.Log.Finder;
import com.groundspeak.cache._1._0._1.Cache.Owner;
import com.jayway.restassured.response.Response;
import com.topografix.gpx._1._0.Gpx;
import com.topografix.gpx._1._0.Gpx.Wpt;

import net.gsak.xmlv1._6.WptExtension;

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
				doImport(file);
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
	private void doImport(File file) {
		System.out.println("Start importing " + file.getName());

		try {
			JAXBContext jc10 = JAXBContext.newInstance(Gpx.class, WptExtension.class, Cache.class);
			Unmarshaller unmarshaller = jc10.createUnmarshaller();

			Gpx gpx = (Gpx) unmarshaller.unmarshal(file);
			List<Wpt> wptList = gpx.getWpt();

			for (Wpt wpt : wptList) {
				// currently our API only supports Geocache waypoints
				if (!wpt.getName().startsWith("GC")) {
					continue;
				}
				GeoCache geoCache = new GeoCache();
				geoCache.setDetails(new GeoCacheDetails());

				List<Log> logs = new ArrayList<>();

				geoCache.setGcCode(wpt.getName());
				geoCache.setCoordinates(new Point(wpt.getLon().doubleValue(), wpt.getLat().doubleValue()));
				if (wpt.getTime() != null) {
					geoCache.setPlacedAt(wpt.getTime().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
				}

				List<Object> any = wpt.getAny();
				for (Object object : any) {
					if (object instanceof Cache) {
						fillBasicData(geoCache, (Cache) object);
						fillLogData(logs, (Cache) object);
					}

					if (object instanceof WptExtension) {
						fillAdditionalData(geoCache, (WptExtension) object);
					}
				}

				Response response = given().headers(headers).contentType(CONTENT_TYPE).expect()
						.get("caches/" + geoCache.getGcCode());

				// if cache does not exist, create it
				if (response.getStatusCode() == Status.NOT_FOUND.getStatusCode()) {
					response = given().headers(headers).contentType(CONTENT_TYPE).body(geoCache).expect()
							.post("caches");

					response.then().assertThat().statusCode(Status.CREATED.getStatusCode());
				}

				for (Log log : logs) {
					response = given().headers(headers).contentType(CONTENT_TYPE).body(log).expect()
							.log().all().post("caches/" + geoCache.getGcCode() + "/logs");

					response.then().assertThat().statusCode(Status.CREATED.getStatusCode());
				}

				// break;
			}
			// Thread.sleep(5);

			System.out.println("Imported " + wptList.size() + " Geocaches from " + file.getName());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error processing file: " + file, e);
			throw new IllegalStateException("Error processing file: " + file, e);
		}
	}

	private void fillBasicData(GeoCache geoCache, Cache cache) {
		geoCache.setId(cache.getId());
		geoCache.setName(cache.getName());
		geoCache.setType(CacheType.of(cache.getType()));

		geoCache.setPlacedBy(cache.getPlacedBy());
		geoCache.setContainer(ContainerType.of(cache.getContainer()));
		geoCache.setDifficulty(Float.parseFloat(cache.getDifficulty()));
		geoCache.setTerrain(Float.parseFloat(cache.getTerrain()));

		Owner owner = cache.getOwner().get(0);
		geoCache.setOwner(new User(owner.getValue(), owner.getId()));

		geoCache.getDetails().setCountry(cache.getCountry());
		geoCache.getDetails().setState(cache.getState());

		geoCache.getDetails().setShortDescription(cache.getShortDescription().get(0).getValue());
		geoCache.getDetails().setLongDescription(cache.getLongDescription().get(0).getValue());

		for (Cache.Attributes attrs : cache.getAttributes()) {
			for (Cache.Attributes.Attribute attr : attrs.getAttribute()) {
				geoCache.getDetails().getAttributes().add(new Attribute(attr.getValue(), attr.getId()));
			}
		}
	}

	private void fillAdditionalData(GeoCache geoCache, WptExtension wptExtension) {
		if (wptExtension.getLonBeforeCorrect() != null && wptExtension.getLatBeforeCorrect() != null) {
			geoCache.getDetails().setOriginalCoordinates(
					new Point(Double.valueOf(wptExtension.getLonBeforeCorrect()),
							Double.valueOf(wptExtension.getLatBeforeCorrect())));
		}

		geoCache.getDetails().setPersonalNote(wptExtension.getGcNote());

		if (wptExtension.getFavPoints() != null) {
			geoCache.getDetails().setFavPoints(wptExtension.getFavPoints().intValue());
		}
	}

	private void fillLogData(List<Log> logs, Cache cache) {
		for (Cache.Logs cacheLogs : cache.getLogs()) {
			for (Cache.Logs.Log log : cacheLogs.getLog()) {
				Finder finder = log.getFinder().get(0);
				Instant instant = Instant.parse(log.getDate());
				LocalDateTime dt = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin"));

				Log cacheLog = new Log(dt, LogType.of(log.getType()),
						new User(finder.getValue(), finder.getId()),
						log.getText().get(0).getValue(), log.getId());

				logs.add(cacheLog);
			}
		}
	}
}

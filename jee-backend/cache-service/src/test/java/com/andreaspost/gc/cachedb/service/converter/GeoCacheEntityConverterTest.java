package com.andreaspost.gc.cachedb.service.converter;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.geojson.Point;
import org.junit.Test;
import org.mongodb.morphia.geo.GeoJson;

import com.andreaspost.gc.cachedb.persistence.entity.AttributeEntity;
import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.persistence.entity.UserEntity;
import com.andreaspost.gc.cachedb.rest.resource.Attribute;
import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.ContainerType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.GeoCacheDetails;
import com.andreaspost.gc.cachedb.rest.resource.User;

/**
 * Tests for {@link GeoCacheEntityConverter}.
 * 
 * @author Andreas Post
 */
public class GeoCacheEntityConverterTest {

	private static final String GCCODE = "GC123";
	private static final String ID = "123";
	private static final String USER_ID = "456";
	private static final String NAME = "Test Cache";
	private static final float DIFFICULTY = 3;
	private static final float TERRAIN = 2.5f;
	private static final String PLACED_BY = "Test Cacher";
	private static final ContainerType CONTAINER = ContainerType.MICRO;
	private static final double LATITUDE = 51.1234;
	private static final double LONGITUDE = 13.4567;
	private static final double ORIG_LATITUDE = 51.4321;
	private static final double ORIG_LONGITUDE = 13.76547;
	private static final String COUNTRY = "Country";
	private static final String STATE = "State";
	private static final String SHORT_DESC = "Short Desc";
	private static final String LONG_DESC = "Long Desc";
	private static final String HINTS = "Hints";
	private static final String ATTRIBUTE_1_NAME = "Scenic view";
	private static final String ATTRIBUTE_1_ID = "8";
	private static final String ATTRIBUTE_2_NAME = "Takes less than an hour";
	private static final String ATTRIBUTE_2_ID = "7";
	private static final String PERSONAL_NOTE = "Solution note";
	private static final Integer FAV_POINTS = Integer.valueOf(99);

	@Test
	public void decodeTest() {
		GeoCacheEntity entity = createDummyGeoCacheEntity();

		GeoCache cache = new GeoCacheEntityConverter().decode(entity);

		assertEquals("GC Code must be the same.", GCCODE, cache.getGcCode());
		assertEquals("ID must be the same.", ID, cache.getId());
		assertEquals("Name must be the same.", NAME, cache.getName());
		assertEquals("Type must be the same.", CacheType.CITO, cache.getType());
		assertEquals(DIFFICULTY, cache.getDifficulty(), 0);
		assertEquals(TERRAIN, cache.getTerrain(), 0);
		assertEquals("Placed at must be the same.", cache.getPlacedAt(), cache.getPlacedAt());
		assertEquals("Placed by must be the same.", PLACED_BY, cache.getPlacedBy());
		assertEquals("Container must be the same.", CONTAINER, cache.getContainer());
		assertEquals(LONGITUDE, cache.getCoordinates().getCoordinates().getLongitude(), 0);
		assertEquals(LATITUDE, cache.getCoordinates().getCoordinates().getLatitude(), 0);
		assertEquals("Owner name must be the same.", PLACED_BY, cache.getOwner().getName());
		assertEquals("Owner ID must be the same.", USER_ID, cache.getOwner().getId());
		assertEquals("Country must be the same.", COUNTRY, cache.getDetails().getCountry());
		assertEquals("State must be the same.", STATE, cache.getDetails().getState());
		assertEquals("Short description must be the same.", SHORT_DESC, cache.getDetails().getShortDescription());
		assertEquals("Long description must be the same.", LONG_DESC, cache.getDetails().getLongDescription());
		assertEquals("Hints must be the same.", HINTS, cache.getDetails().getEncodedHints());
		assertEquals("Personal Note must be the same.", PERSONAL_NOTE, cache.getDetails().getPersonalNote());
		assertEquals("FavPoints must be the same.", FAV_POINTS, cache.getDetails().getFavPoints());

		assertEquals(ORIG_LONGITUDE, cache.getDetails().getOriginalCoordinates().getCoordinates().getLongitude(), 0);
		assertEquals(ORIG_LATITUDE, cache.getDetails().getOriginalCoordinates().getCoordinates().getLatitude(), 0);

		assertEquals("Attributes size must be the same.", entity.getAttributes().size(), cache.getDetails().getAttributes().size());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_1_NAME, cache.getDetails().getAttributes().get(0).getName());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_1_ID, cache.getDetails().getAttributes().get(0).getId());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_2_NAME, cache.getDetails().getAttributes().get(1).getName());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_2_ID, cache.getDetails().getAttributes().get(1).getId());
	}

	@Test
	public void encodeTest() {
		GeoCache cache = createDummyGeoCache();

		GeoCacheEntity entity = new GeoCacheEntityConverter().encode(cache);

		assertEquals("GC Code must be the same.", GCCODE, entity.getGcCode());
		assertEquals("ID must be the same.", ID, entity.getId());
		assertEquals("Name must be the same.", NAME, entity.getName());
		assertEquals("Type must be the same.", CacheType.CITO.getName(), entity.getType());
		assertEquals(DIFFICULTY, entity.getDifficulty(), 0);
		assertEquals(TERRAIN, entity.getTerrain(), 0);
		assertEquals("Placed at must be the same.", cache.getPlacedAt(), entity.getPlacedAt());
		assertEquals("Placed by must be the same.", PLACED_BY, entity.getPlacedBy());
		assertEquals("Container must be the same.", CONTAINER.getName(), entity.getContainer());
		assertEquals(LONGITUDE, entity.getCoordinates().getLongitude(), 0);
		assertEquals(LATITUDE, entity.getCoordinates().getLatitude(), 0);
		assertEquals("Owner name must be the same.", PLACED_BY, entity.getOwner().getName());
		assertEquals("Owner ID must be the same.", USER_ID, entity.getOwner().getId());
		assertEquals("Country must be the same.", COUNTRY, entity.getCountry());
		assertEquals("State must be the same.", STATE, entity.getState());
		assertEquals("Short description must be the same.", SHORT_DESC, entity.getShortDescription());
		assertEquals("Long description must be the same.", LONG_DESC, entity.getLongDescription());
		assertEquals("Hints must be the same.", HINTS, entity.getEncodedHints());
		assertEquals("Personal Note must be the same.", PERSONAL_NOTE, entity.getPersonalNote());
		assertEquals("FavPoints must be the same.", FAV_POINTS, entity.getFavPoints());

		assertEquals(ORIG_LONGITUDE, entity.getOriginalCoordinates().getLongitude(), 0);
		assertEquals(ORIG_LATITUDE, entity.getOriginalCoordinates().getLatitude(), 0);

		assertEquals("Attributes size must be the same.", cache.getDetails().getAttributes().size(), entity.getAttributes().size());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_1_NAME, entity.getAttributes().get(0).getName());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_1_ID, entity.getAttributes().get(0).getId());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_2_NAME, entity.getAttributes().get(1).getName());
		assertEquals("Attributes size must be the same.", ATTRIBUTE_2_ID, entity.getAttributes().get(1).getId());
	}

	/**
	 * Returns a {@link GeoCache} instance for converting.
	 * 
	 * @return
	 */
	private GeoCache createDummyGeoCache() {
		GeoCache geoCache = new GeoCache();

		geoCache.setGcCode(GCCODE);
		geoCache.setId(ID);
		geoCache.setName(NAME);
		geoCache.setType(CacheType.CITO);
		geoCache.setDifficulty(DIFFICULTY);
		geoCache.setTerrain(TERRAIN);
		geoCache.setPlacedAt(LocalDateTime.now());
		geoCache.setPlacedBy(PLACED_BY);
		geoCache.setContainer(CONTAINER);
		geoCache.setCoordinates(new Point(LONGITUDE, LATITUDE));
		geoCache.setOwner(new User(PLACED_BY, USER_ID));

		GeoCacheDetails details = new GeoCacheDetails();

		details.setCountry(COUNTRY);
		details.setState(STATE);
		details.setShortDescription(SHORT_DESC);
		details.setLongDescription(LONG_DESC);
		details.setEncodedHints(HINTS);
		details.setPersonalNote(PERSONAL_NOTE);
		details.setFavPoints(FAV_POINTS);

		details.getAttributes().add(new Attribute(ATTRIBUTE_1_NAME, ATTRIBUTE_1_ID));
		details.getAttributes().add(new Attribute(ATTRIBUTE_2_NAME, ATTRIBUTE_2_ID));

		details.setOriginalCoordinates(new Point(ORIG_LONGITUDE, ORIG_LATITUDE));

		geoCache.setDetails(details);

		return geoCache;
	}

	/**
	 * Returns a {@link GeoCacheEntity} instance for converting.
	 * 
	 * @return
	 */
	private GeoCacheEntity createDummyGeoCacheEntity() {
		GeoCacheEntity geoCache = new GeoCacheEntity();

		geoCache.setGcCode(GCCODE);
		geoCache.setId(ID);
		geoCache.setName(NAME);
		geoCache.setType(CacheType.CITO.getName());
		geoCache.setDifficulty(DIFFICULTY);
		geoCache.setTerrain(TERRAIN);
		geoCache.setPlacedAt(LocalDateTime.now());
		geoCache.setPlacedBy(PLACED_BY);
		geoCache.setContainer(CONTAINER.getName());
		geoCache.setCoordinates(GeoJson.point(LATITUDE, LONGITUDE));
		geoCache.setOwner(new UserEntity(PLACED_BY, USER_ID));

		geoCache.setCountry(COUNTRY);
		geoCache.setState(STATE);
		geoCache.setShortDescription(SHORT_DESC);
		geoCache.setLongDescription(LONG_DESC);
		geoCache.setEncodedHints(HINTS);
		geoCache.setPersonalNote(PERSONAL_NOTE);
		geoCache.setFavPoints(FAV_POINTS);

		geoCache.getAttributes().add(new AttributeEntity(ATTRIBUTE_1_NAME, ATTRIBUTE_1_ID));
		geoCache.getAttributes().add(new AttributeEntity(ATTRIBUTE_2_NAME, ATTRIBUTE_2_ID));

		geoCache.setOriginalCoordinates(GeoJson.point(ORIG_LATITUDE, ORIG_LONGITUDE));

		return geoCache;
	}
}

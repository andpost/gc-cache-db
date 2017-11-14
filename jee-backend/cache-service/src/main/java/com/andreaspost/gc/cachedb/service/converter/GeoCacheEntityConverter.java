package com.andreaspost.gc.cachedb.service.converter;

import org.geojson.Point;
import org.mongodb.morphia.geo.GeoJson;

import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;

/**
 * Converts {@link GeoCacheEntity} to {@link GeoCache} and back.
 * 
 * @author Andreas Post
 */
public class GeoCacheEntityConverter extends AbstractEntityConverter<GeoCache, GeoCacheEntity> {

	private static final UserEntityConverter userConverter = new UserEntityConverter();

	private static final LogEntityConverter logConverter = new LogEntityConverter();

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public GeoCache decode(GeoCacheEntity entity) {
		GeoCache geoCache = new GeoCache();

		geoCache.setGcCode(entity.getGcCode());
		geoCache.setId(entity.getId());
		geoCache.setName(entity.getName());
		geoCache.setType(CacheType.of(entity.getType()));
		geoCache.setDifficulty(entity.getDifficulty());
		geoCache.setTerrain(entity.getTerrain());
		geoCache.setCountry(entity.getCountry());
		geoCache.setPlacedBy(entity.getPlacedBy());
		geoCache.setContainer(entity.getContainer());
		geoCache.setState(entity.getState());
		geoCache.setShortDescription(entity.getShortDescription());
		geoCache.setLongDescription(entity.getLongDescription());
		geoCache.setEncodedHints(entity.getEncodedHints());

		geoCache.setCoordinates(
				new Point(entity.getCoordinates().getLongitude(), entity.getCoordinates().getLatitude()));

		if (entity.getOwner() != null) {
			geoCache.setOwner(userConverter.decode(entity.getOwner()));
		}

		geoCache.setLogs(logConverter.decode(entity.getLogs()));

		return geoCache;
	}

	/**
	 * 
	 * @param geoCache
	 * @return
	 */
	public GeoCacheEntity encode(GeoCache geoCache) {
		GeoCacheEntity entity = new GeoCacheEntity();

		entity.setGcCode(geoCache.getGcCode());
		entity.setId(geoCache.getId());
		entity.setName(geoCache.getName());
		entity.setType(geoCache.getType().getName());
		entity.setDifficulty(geoCache.getDifficulty());
		entity.setTerrain(geoCache.getTerrain());
		entity.setCountry(geoCache.getCountry());
		entity.setPlacedBy(geoCache.getPlacedBy());
		entity.setContainer(geoCache.getContainer());
		entity.setState(geoCache.getState());
		entity.setShortDescription(geoCache.getShortDescription());
		entity.setLongDescription(geoCache.getLongDescription());
		entity.setEncodedHints(geoCache.getEncodedHints());

		entity.setCoordinates(GeoJson.point(geoCache.getCoordinates().getCoordinates().getLatitude(),
				geoCache.getCoordinates().getCoordinates().getLongitude()));

		if (geoCache.getOwner() != null) {
			entity.setOwner(userConverter.encode(geoCache.getOwner()));
		}

		entity.setLogs(logConverter.encode(geoCache.getLogs()));

		return entity;
	}
}

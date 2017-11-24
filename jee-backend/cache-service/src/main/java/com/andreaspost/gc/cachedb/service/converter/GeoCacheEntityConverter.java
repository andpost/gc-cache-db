package com.andreaspost.gc.cachedb.service.converter;

import org.geojson.Point;
import org.mongodb.morphia.geo.GeoJson;

import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.rest.resource.CacheType;
import com.andreaspost.gc.cachedb.rest.resource.ContainerType;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.rest.resource.GeoCacheDetails;

/**
 * Converts {@link GeoCacheEntity} to {@link GeoCache} and back.
 * 
 * @author Andreas Post
 */
public class GeoCacheEntityConverter extends AbstractEntityConverter<GeoCache, GeoCacheEntity> {

	private static final UserEntityConverter userConverter = new UserEntityConverter();

	private static final AttributesEntityConverter attributesConverter = new AttributesEntityConverter();

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
		geoCache.setPlacedAt(entity.getPlacedAt());
		geoCache.setPlacedBy(entity.getPlacedBy());
		geoCache.setContainer(ContainerType.of(entity.getContainer()));

		geoCache.setCoordinates(
				new Point(entity.getCoordinates().getLongitude(), entity.getCoordinates().getLatitude()));

		if (entity.getOwner() != null) {
			geoCache.setOwner(userConverter.decode(entity.getOwner()));
		}

		GeoCacheDetails details = new GeoCacheDetails();

		details.setCountry(entity.getCountry());
		details.setState(entity.getState());
		details.setShortDescription(entity.getShortDescription());
		details.setLongDescription(entity.getLongDescription());
		details.setEncodedHints(entity.getEncodedHints());

		details.setAttributes(attributesConverter.decode(entity.getAttributes()));

		geoCache.setDetails(details);

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
		entity.setPlacedAt(geoCache.getPlacedAt());
		entity.setPlacedBy(geoCache.getPlacedBy());
		entity.setContainer(geoCache.getContainer().getName());

		entity.setCoordinates(GeoJson.point(geoCache.getCoordinates().getCoordinates().getLatitude(),
				geoCache.getCoordinates().getCoordinates().getLongitude()));

		if (geoCache.getOwner() != null) {
			entity.setOwner(userConverter.encode(geoCache.getOwner()));
		}

		if (geoCache.getDetails() != null) {
			entity.setCountry(geoCache.getDetails().getCountry());
			entity.setState(geoCache.getDetails().getState());
			entity.setShortDescription(geoCache.getDetails().getShortDescription());
			entity.setLongDescription(geoCache.getDetails().getLongDescription());
			entity.setEncodedHints(geoCache.getDetails().getEncodedHints());
			entity.setAttributes(attributesConverter.encode(geoCache.getDetails().getAttributes()));
		}

		return entity;
	}
}

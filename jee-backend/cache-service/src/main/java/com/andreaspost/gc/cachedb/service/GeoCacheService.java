package com.andreaspost.gc.cachedb.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;

import com.andreaspost.gc.cachedb.persistence.PersistenceService;
import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.service.converter.GeoCacheEntityConverter;

/**
 * The geocache service.
 * 
 * @author Andreas Post
 */
@Stateless
@LocalBean
public class GeoCacheService {

	@Inject
	PersistenceService persistenceService;

	private GeoCacheEntityConverter entityConverter = new GeoCacheEntityConverter();

	/**
	 * Get a geocache by id.
	 * 
	 * @param id
	 *            String representation of object id.
	 * @param expandDetails
	 *            If true returnes all data of the geocache.
	 * @return {@link GeoCache} if match found or null.
	 */
	public GeoCache getGeoCache(String id, boolean expandDetails) {
		GeoCacheEntity entity = persistenceService.getGeoCache(new ObjectId(id), expandDetails);

		if (entity == null) {
			return null;
		}

		return entityConverter.decode(entity);
	}

	/**
	 * Get a geocache by GC Code.
	 * 
	 * @param gcCode
	 *            GC Code (unique identifier)
	 * @param expandDetails
	 *            If true returnes all data of the geocache.
	 * @return {@link GeoCache} if match found or null.
	 */
	public GeoCache getGeoCacheByGcCode(String gcCode, boolean expandDetails) {
		GeoCacheEntity entity = persistenceService.getGeoCacheByGcCode(gcCode, expandDetails);

		if (entity == null) {
			return null;
		}

		return entityConverter.decode(entity);
	}

	/**
	 * Delete a geocache by id.
	 * 
	 * @param id
	 */
	public void deleteGeoCache(String id) {
		persistenceService.deleteGeoCache(new ObjectId(id));
	}

	/**
	 * Create a new geocache.
	 * 
	 * @param geoCache
	 * @return The new geocache including its id.
	 */
	public GeoCache createGeoCache(GeoCache geoCache) {

		GeoCacheEntity entity = entityConverter.encode(geoCache);

		GeoCacheEntity persistentEntity = persistenceService.getGeoCacheByGcCode(geoCache.getGcCode(), true);

		if (persistentEntity != null) {
			mergeEntities(entity, persistentEntity);
		}

		entity = persistenceService.createGeoCache(entity);

		return entityConverter.decode(entity);
	}

	/**
	 * Returns a list of nearest geocaches.
	 * 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param expandDetails
	 *            If true returns all data of the geocache.
	 * @return
	 */
	public List<GeoCache> listGeoCaches(double lat, double lon, int radius, boolean expandDetails) {
		List<GeoCacheEntity> entityList = persistenceService.listGeoCaches(lat, lon, radius, expandDetails);

		return entityConverter.decode(entityList);
	}

	/**
	 * Merges mergeFrom into mergeTo entity.
	 * 
	 * @param mergeTo
	 * @param mergeFrom
	 */
	private void mergeEntities(GeoCacheEntity mergeTo, GeoCacheEntity mergeFrom) {
		mergeTo.setObjectId(mergeFrom.getObjectId());

		mergeTo.getLogs().addAll(mergeFrom.getLogs());
	}
}

package com.andreaspost.gc.cachedb.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import com.andreaspost.gc.cachedb.interceptors.MethodLoggingInterceptor;
import com.andreaspost.gc.cachedb.persistence.PersistenceService;
import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.persistence.exception.DuplicateGeoCacheException;
import com.andreaspost.gc.cachedb.rest.resource.GeoCache;
import com.andreaspost.gc.cachedb.service.converter.GeoCacheEntityConverter;

/**
 * The geocache service.
 * 
 * @author Andreas Post
 */
@Stateless
@LocalBean
@Interceptors({ MethodLoggingInterceptor.class })
public class GeoCacheService {

	private static final Logger LOG = Logger.getLogger(GeoCacheService.class.getName());

	@Inject
	PersistenceService persistenceService;

	private GeoCacheEntityConverter entityConverter = new GeoCacheEntityConverter();

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
	 * Delete a geocache by GC Code.
	 * 
	 * @param gcCode
	 *            GC Code (unique identifier)
	 */
	public void deleteGeoCacheByGcCode(String gcCode) {
		persistenceService.deleteGeoCacheByGcCode(gcCode);
	}

	/**
	 * Create a new geocache.
	 * 
	 * @param geoCache
	 * @return The new or updated geocache including its id.
	 */
	public GeoCache createGeoCache(GeoCache geoCache) throws DuplicateGeoCacheException {

		GeoCacheEntity entity = entityConverter.encode(geoCache);

		entity = persistenceService.createGeoCache(entity);

		return entityConverter.decode(entity);
	}

	/**
	 * Create or update a geocache.
	 * 
	 * @param geoCache
	 * @return The new or updated geocache including its id.
	 */
	public GeoCache createOrUpdateGeoCache(GeoCache geoCache) {

		GeoCacheEntity entity = entityConverter.encode(geoCache);

		GeoCacheEntity persistentEntity = persistenceService.getGeoCacheByGcCode(geoCache.getGcCode(), true);

		if (persistentEntity != null) {
			mergeEntities(entity, persistentEntity);
		}

		try {
			entity = persistenceService.createGeoCache(entity);
		} catch (DuplicateGeoCacheException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

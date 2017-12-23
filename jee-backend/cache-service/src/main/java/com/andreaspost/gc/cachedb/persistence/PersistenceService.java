package com.andreaspost.gc.cachedb.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.geo.PointBuilder;
import org.mongodb.morphia.query.Query;

import com.andreaspost.gc.cachedb.interceptors.MethodLoggingInterceptor;
import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.persistence.entity.LogEntity;
import com.andreaspost.gc.cachedb.persistence.exception.DuplicateGeoCacheException;
import com.mongodb.DuplicateKeyException;
import com.mongodb.WriteResult;

/**
 * Service for our persistence stuff.
 * 
 * @author Andreas Post
 */
@Stateless
@Interceptors({ MethodLoggingInterceptor.class })
public class PersistenceService {

	private static final Logger LOG = Logger.getLogger(PersistenceService.class.getName());

	@Inject
	MongoDBClientProvider mongoDBClientProvider;

	/**
	 * Saves the given {@link GeoCacheEntity} as new entity. The returning
	 * entity contains the generated id.
	 * 
	 * @param geoCache
	 *            the entity to store.
	 * @return the entity with id
	 */
	public GeoCacheEntity createGeoCache(GeoCacheEntity geoCache) throws DuplicateGeoCacheException {
		try {
			mongoDBClientProvider.getDatastore().save(geoCache);
		} catch (DuplicateKeyException e) {
			LOG.log(Level.WARNING, "Duplicate Key: " + e.getMessage(), e);
			throw new DuplicateGeoCacheException("Duplicate Key: " + geoCache.getGcCode(), e);
		}
		return geoCache;
	}

	/**
	 * Returns a {@link GeoCacheEntity} by given gcCode.
	 * 
	 * @param gcCode
	 * @param withDetails
	 *            If true, returns more details.
	 * @param withLogs
	 *            If true, returns available logs.
	 * @return
	 */
	public GeoCacheEntity getGeoCacheByGcCode(String gcCode, boolean withDetails, boolean withLogs) {

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

		if (!withDetails) {
			query = query.project("country", false);
			query = query.project("state", false);
			query = query.project("shortDescription", false);
			query = query.project("longDescription", false);
			query = query.project("encodedHints", false);
			query = query.project("attributes", false);
			query = query.project("originalCoordinates", false);
			query = query.project("reviewer", false);
			query = query.project("personalNote", false);
			query = query.project("favPoints", false);
		}

		if (!withLogs) {
			query = query.project("logs", false);
		}

		return query.field("gcCode").equal(gcCode).get();
	}

	public boolean addLog(String gcCode, LogEntity log) {
		GeoCacheEntity geoCache = getGeoCacheByGcCode(gcCode, false, true);

		if (geoCache == null) {
			return false;
		}

		geoCache.getLogs().add(log);

		mongoDBClientProvider.getDatastore().merge(geoCache);

		return true;
	}

	/**
	 * Delete a geocache by GC Code.
	 * 
	 * @param id
	 */
	public void deleteGeoCacheByGcCode(String gcCode) {
		LOG.info("deleteGeoCacheByGcCode: " + gcCode);

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

		query.field("gcCode").equal(gcCode);

		WriteResult writeResult = mongoDBClientProvider.getDatastore().delete(query);

		LOG.info(writeResult.toString());
	}

	/**
	 * List geocache by coords and radius.
	 * 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param withDetails
	 *            If true returnes all data of the geocache.
	 * @return
	 */
	public List<GeoCacheEntity> listGeoCaches(double lat, double lon, int radius, boolean withDetails) {
		PointBuilder builder = PointBuilder.pointBuilder();
		Point point = builder.latitude(lat).longitude(lon).build();

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

		if (!withDetails) {
			query = query.project("country", false);
			query = query.project("state", false);
			query = query.project("shortDescription", false);
			query = query.project("longDescription", false);
			query = query.project("encodedHints", false);
			query = query.project("attributes", false);
			query = query.project("originalCoordinates", false);
			query = query.project("reviewer", false);
			query = query.project("personalNote", false);
			query = query.project("favPoints", false);
		}

		// TODO enable logs in lists
		query = query.project("logs", false);

		return query.field("coordinates").near(point, radius).asList();
	}

	public String hello() {
		return "hello";
	}
}

package com.andreaspost.gc.cachedb.persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.geo.PointBuilder;
import org.mongodb.morphia.query.Query;

import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
import com.andreaspost.gc.cachedb.persistence.exception.DuplicateGeoCacheException;
import com.mongodb.DuplicateKeyException;
import com.mongodb.WriteResult;

/**
 * Service for our persistence stuff.
 * 
 * @author Andreas Post
 */
@Stateless
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

	public GeoCacheEntity getGeoCacheByGcCode(String gcCode, boolean expandDetails) {

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

		if (!expandDetails) {
			query = query.retrievedFields(false, "logs");
		}

		return query.field("gcCode").equal(gcCode).get();

		// if (!expandDetails) {
		// return
		// mongoDBClientProvider.getDatastore().find(GeoCacheEntity.class,
		// Mapper.ID_KEY, id)
		// .retrievedFields(false, "details").get();
		// } else {
		// return mongoDBClientProvider.getDatastore().get(GeoCacheEntity.class,
		// id);
		// }
	}

	/**
	 * Delete a geocache by id.
	 * 
	 * @param id
	 */
	public void deleteGeoCache(ObjectId id) {
		LOG.info("deleteGeoCache: " + id);

		WriteResult writeResult = mongoDBClientProvider.getDatastore().delete(GeoCacheEntity.class, id);

		LOG.info(writeResult.toString());
	}

	/**
	 * List geocache by coords and radius.
	 * 
	 * @param lat
	 * @param lon
	 * @param radius
	 * @param expandDetails
	 *            If true returnes all data of the geocache.
	 * @return
	 */
	public List<GeoCacheEntity> listGeoCaches(double lat, double lon, int radius, boolean expandDetails) {
		PointBuilder builder = PointBuilder.pointBuilder();
		Point point = builder.latitude(lat).longitude(lon).build();

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

		if (!expandDetails) {
			query = query.retrievedFields(false, "logs");
		}

		return query.field("coordinates").near(point, radius).asList();
	}

	public String hello() {
		return "hello";
	}
}

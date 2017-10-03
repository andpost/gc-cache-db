package com.andreaspost.gc.cachedb.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.mongodb.morphia.geo.Point;
import org.mongodb.morphia.geo.PointBuilder;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;

import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
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
	public GeoCacheEntity createGeoCache(GeoCacheEntity geoCache) {

		mongoDBClientProvider.getDatastore().save(geoCache);

		return geoCache;
	}

	/**
	 * Retrieve an geocache entity by id.
	 * 
	 * @param id
	 *            The object id of the entity.
	 * @param expandDetails
	 *            If true returns all data of the geocache.
	 * @return
	 */
	public GeoCacheEntity getGeoCache(ObjectId id, boolean expandDetails) {
		/*
		 * This is for showing how fields can be left out. The query would be
		 * like:
		 * 
		 * db.getCollection('cachedb').find({_id: ObjectId('[id]')},{'details':
		 * 0})
		 */
		if (!expandDetails) {
			return mongoDBClientProvider.getDatastore().find(GeoCacheEntity.class, Mapper.ID_KEY, id)
					.retrievedFields(false, "details").get();
		} else {
			return mongoDBClientProvider.getDatastore().get(GeoCacheEntity.class, id);
		}
	}

	public GeoCacheEntity getGeoCacheByGcCode(String gcCode, boolean expandDetails) {

		Query<GeoCacheEntity> query = mongoDBClientProvider.getDatastore().createQuery(GeoCacheEntity.class);

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
			query = query.retrievedFields(false, "details");
		}

		return query.field("location").near(point, radius).asList();
	}
}

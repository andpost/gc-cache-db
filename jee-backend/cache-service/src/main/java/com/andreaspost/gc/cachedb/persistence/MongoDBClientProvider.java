package com.andreaspost.gc.cachedb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

/**
 * 
 * @author Andreas Post
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class MongoDBClientProvider {

	private static final Logger LOG = Logger.getLogger(MongoDBClientProvider.class.getName());

	private static final String PROPERTIES_FILE = "persistence.properties";

	private static final String PROPERTY_PREFIX = "mongodb.database.";

	private MongoClient mongoClient = null;

	private Morphia morphia;

	private Datastore datastore;

	private Properties properties;

	@PostConstruct
	public void init() {
		loadPersistenceProperties();

		String host = properties.getProperty(PROPERTY_PREFIX + "host");
		int port = Integer.parseInt(properties.getProperty(PROPERTY_PREFIX + "port"));
		String dbName = properties.getProperty(PROPERTY_PREFIX + "db");

		MongoClientOptions settings = MongoClientOptions.builder()
				.codecRegistry(com.mongodb.MongoClient.getDefaultCodecRegistry()).build();
		mongoClient = new MongoClient(new ServerAddress(host, port), settings);
		morphia = new Morphia();

		// Morphia.mapPackages doesn't work for war files :(
		getMappingClasses().stream().forEach(clazz -> morphia.map(clazz));

		datastore = morphia.createDatastore(mongoClient, dbName);
		datastore.ensureIndexes();
	}

	@Lock(LockType.READ)
	public Datastore getDatastore() {
		return datastore;
	}

	/**
	 * 
	 */
	@PreDestroy
	public void preDestroy() {
		LOG.info("preDestroy()");
		if (mongoClient != null) {
			mongoClient.close();
		}
	}

	/**
	 * Load properties from file.
	 */
	private void loadPersistenceProperties() {
		String resourceName = PROPERTIES_FILE;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		properties = new Properties();

		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			properties.load(resourceStream);
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Error loading " + PROPERTIES_FILE, e);
		}
	}

	/**
	 * Get a list of entity classes to map for morphia.
	 * 
	 * @return
	 */
	private List<Class<?>> getMappingClasses() {
		List<Class<?>> clazzes = new ArrayList<>();

		String mapEntities = properties.getProperty("morphia.map.entities");

		if (mapEntities != null && !mapEntities.trim().isEmpty()) {
			String[] split = mapEntities.split(",");

			for (String className : split) {
				try {
					clazzes.add(Class.forName(className));
				} catch (ClassNotFoundException e) {
					LOG.log(Level.WARNING, "Error loading class: " + className, e);
				}
			}
		}

		return clazzes;
	}

}

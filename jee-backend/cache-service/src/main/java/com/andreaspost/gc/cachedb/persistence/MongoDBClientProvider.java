package com.andreaspost.gc.cachedb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
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
import org.mongodb.morphia.utils.ReflectionUtils;

import com.andreaspost.gc.cachedb.persistence.entity.GeoCacheEntity;
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

	@PostConstruct
	public void init() {
		Properties properties = getPersistenceProperties();

		String host = properties.getProperty(PROPERTY_PREFIX + "host");
		int port = Integer.parseInt(properties.getProperty(PROPERTY_PREFIX + "port"));
		String dbName = properties.getProperty(PROPERTY_PREFIX + "db");

		MongoClientOptions settings = MongoClientOptions.builder()
				.codecRegistry(com.mongodb.MongoClient.getDefaultCodecRegistry()).build();
		mongoClient = new MongoClient(new ServerAddress(host, port), settings);
		morphia = new Morphia();

		String mapPackage = properties.getProperty("morphia.map.package");

		if (mapPackage != null) {
			LOG.info(mapPackage);
			try {
				for (final Class clazz : getClasses(mapPackage)) {
					LOG.info(clazz.getName());
				}

				for (final Class clazz : ReflectionUtils.getClasses(mapPackage,
						morphia.getMapper().getOptions().isMapSubPackages())) {
					LOG.info(clazz.getName());
				}
			} catch (ClassNotFoundException | IOException e) {
				LOG.log(Level.SEVERE, "Error mapping package " + mapPackage, e);
			}
			// morphia.mapPackage(mapPackage);
		}
		// TODO Fix Issue with package mapping
		morphia.map(GeoCacheEntity.class);

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

	private Properties getPersistenceProperties() {
		String resourceName = PROPERTIES_FILE;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();

		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Error loading " + PROPERTIES_FILE, e);
		}
		return props;
	}

	public static Set<Class<?>> getClasses(final String packageName) throws IOException, ClassNotFoundException {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		final String path = packageName.replace('.', '/');
		final Enumeration<URL> resources = loader.getResources(path);
		if (resources != null) {
			while (resources.hasMoreElements()) {
				String filePath = resources.nextElement().getFile();
				// WINDOWS HACK
				if (filePath.indexOf("%20") > 0) {
					filePath = filePath.replaceAll("%20", " ");
				}
				if (filePath.indexOf("\\") > 0) {
					filePath = filePath.replaceAll("\\", "/");
				}
				// # in the jar name
				if (filePath.indexOf("%23") > 0) {
					filePath = filePath.replaceAll("%23", "#");
				}

				if (filePath != null) {
					if (filePath.indexOf(".war") > 0) {
						String jarPath = filePath.substring(0, filePath.indexOf(".war") + 4);
						// WINDOWS HACK
						if (jarPath.contains(":")) {
							jarPath = jarPath.substring(1);
						}
						classes.addAll(ReflectionUtils.getFromJARFile(loader, jarPath, path, true));
					}
				}
			}
		}
		return classes;
	}
}

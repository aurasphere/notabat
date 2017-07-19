package co.aurasphere.notabat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for handling a property file.
 * 
 * @author Donato Rimenti
 * 
 */
public class PropertiesController {

	/**
	 * The property file to handle.
	 */
	private File propertyFile;

	/**
	 * The properties handled by this controller.
	 */
	private final Properties PROPERTIES = new Properties();

	/**
	 * Instantiates a new PropertiesController.
	 *
	 * @param propertyFile
	 *            the {@link #propertyFile}.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PropertiesController(String propertyFile) throws IOException {
		this(new File(propertyFile));
	}

	/**
	 * Instantiates a new PropertiesController.
	 *
	 * @param propertyFile
	 *            the {@link #propertyFile}.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PropertiesController(File propertyFile) throws IOException {
		this.propertyFile = propertyFile;

		// If the file directory structure doesn't exist, creates it.
		// If the parent folder doesn't exist, creates it.
		File parentFile = propertyFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		// Creates a new file if it doesn't exist yet.
		propertyFile.createNewFile();

		// Loads the properties.
		PROPERTIES.load(new FileInputStream(propertyFile));
	}

	/**
	 * Instantiates a new PropertiesController.
	 *
	 * @param propertyFile
	 *            the {@link #propertyFile}.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public PropertiesController(InputStream propertyFile) throws IOException {
		PROPERTIES.load(propertyFile);
	}

	/**
	 * Reloads the properties from FS.
	 * 
	 * @throws IOException
	 */
	public void reload() throws IOException {
		if (propertyFile != null) {
			PROPERTIES.load(new FileInputStream(propertyFile));
		}
	}

	/**
	 * Gets a property.
	 *
	 * @param key
	 *            the property's key.
	 * @return a property.
	 */
	public String getProperty(String key) {
		return PROPERTIES.getProperty(key);
	}

	/**
	 * Gets a property as an integer.
	 *
	 * @param key
	 *            the property's key.
	 * @return a property.
	 */
	public Integer getPropertyAsInteger(String key) {
		return Integer.valueOf(getProperty(key));
	}

	/**
	 * Gets a property as long.
	 *
	 * @param key
	 *            the property's key.
	 * @return a property.
	 */
	public Long getPropertyAsLong(String key) {
		return Long.valueOf(getProperty(key));
	}

	/**
	 * Gets a property as a file.
	 *
	 * @param key
	 *            the property's key.
	 * @return a property.
	 */
	public File getPropertyAsFile(String key) {
		return new File(getProperty(key));
	}

	/**
	 * Saves the current properties back to the property file.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void save() throws IOException {
		if (propertyFile != null) {
			FileOutputStream fos = new FileOutputStream(propertyFile);
			PROPERTIES.store(fos, null);
		}
	}

	/**
	 * Sets a property and saves the status back to the property file.
	 *
	 * @param key
	 *            the property's key.
	 * @param value
	 *            the property's value.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void setProperty(String key, String value) throws IOException {
		PROPERTIES.setProperty(key, value);
		save();
	}

	/**
	 * Gets the {@link #properties}.
	 *
	 * @return the {@link #properties}.
	 */
	public Properties getProperties() {
		return this.PROPERTIES;
	}

	/**
	 * Gets the {@link #propertyFile}.
	 *
	 * @return the {@link #propertyFile}.
	 */
	public File getPropertyFile() {
		return this.propertyFile;
	}

}
package co.aurasphere.notabat.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import co.aurasphere.notabat.utils.Kernel32;
import co.aurasphere.notabat.utils.PropertiesController;

/**
 * Test class for Notabat.
 * 
 * @author Donato Rimenti
 *
 */
public class TestNotabat {

	/**
	 * Name of the configuration file for this app.
	 */
	private static final String CONFIG_FILE_NAME = "notabat.properties";

	/**
	 * Property key for the polling frequency for the battery status.
	 */
	private static final String POLLING_FREQUENCY_KEY = "notabat.battery.polling.frequency";

	/**
	 * Property key for the percentage of low battery used to fire
	 * notifications.
	 */
	private static final String BATTERY_LOW_PERCENTAGE_THRESHOLD_KEY = "notabat.battery.low.threshold";

	/**
	 * Property key for the percentage of full battery used to fire
	 * notifications.
	 */
	private static final String BATTERY_FULL_PERCENTAGE_THRESHOLD_KEY = "notabat.battery.full.threshold";

	/**
	 * Tests the availability of battery status reading by loading the Windows
	 * Kernel class and trying to read the battery life percent and ac line
	 * status.
	 */
	@Test
	public void testBatteryReading() {
		// Loads the battery status.
		Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();
		Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);

		// Checks the readings.
		Assert.assertNotNull(batteryStatus.getBatteryLifePercent());
		Assert.assertNotNull(batteryStatus.getACLineStatusString());
	}

	/**
	 * Tests the app configuration by loading a properties file and reading the
	 * required configuration parameters.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAppConfiguration() throws IOException {
		// Loads the configuration
		PropertiesController configuration = new PropertiesController(
				getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));

		// Checks that the required properties are not null.
		Assert.assertNotNull(configuration.getPropertyAsInteger(BATTERY_LOW_PERCENTAGE_THRESHOLD_KEY));
		Assert.assertNotNull(configuration.getPropertyAsInteger(BATTERY_FULL_PERCENTAGE_THRESHOLD_KEY));
		Assert.assertNotNull(configuration.getPropertyAsLong(POLLING_FREQUENCY_KEY));
	}

}

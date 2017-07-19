package co.aurasphere.notabat;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import co.aurasphere.notabat.ui.AlertWindow;
import co.aurasphere.notabat.utils.Enums.ChargingStatus;
import co.aurasphere.notabat.utils.Enums.NotificationType;
import co.aurasphere.notabat.utils.Kernel32;
import co.aurasphere.notabat.utils.PropertiesController;

/**
 * Notabat main class. Fires notifications when the battery is low or full,
 * optionally playing an audio clip.
 * 
 * @author Donato Rimenti
 *
 */
public class Notabat {

	/**
	 * This app's name.
	 */
	public static final String APP_NAME = "Notabat";

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
	 * Property key for the audio to be played when the battery is low.
	 */
	private static final String BATTERY_LOW_NOTIF_AUDIO_KEY = "notabat.battery.low.audio";

	/**
	 * Property key for the audio to be played when the battery is full.
	 */
	private static final String BATTERY_FULL_NOTIF_AUDIO_KEY = "notabat.battery.full.audio";

	/**
	 * Controller for this app configuration.
	 */
	private static PropertiesController properties;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments (none).
	 */
	public static void main(String[] args) {
		// Determine what the GraphicsDevice can support.
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		// If shaped windows aren't supported, exit.
		if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
			System.err.println("Shaped windows are not supported");
			System.exit(0);
		}

		// Instantiates the battery status monitor.
		Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();

		// Loads the app configuration.
		loadAppConfig();

		// Main loop.
		for (;;) {

			// Reloads the properties from FS.
			try {
				properties.reload();
			} catch (IOException e) {
				System.out.println("Error while reloading the properties from FS.");
				e.printStackTrace();
			}

			// Gets the battery level.
			Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
			int batteryPercentage = batteryStatus.getBatteryLifePercent();
			ChargingStatus acStatus = batteryStatus.getACLineStatusString();
			int lifeTime = batteryStatus.getBatteryLifeTime() / 60;

			// If the battery is low and not charging, shows the low level
			// notify.
			if (batteryPercentage <= properties.getPropertyAsInteger(BATTERY_LOW_PERCENTAGE_THRESHOLD_KEY)
					&& acStatus.equals(ChargingStatus.OFFLINE)) {
				showWindow(batteryPercentage, lifeTime, NotificationType.BATTERY_LOW);
				playNotification(properties.getProperty(BATTERY_LOW_NOTIF_AUDIO_KEY));
			}

			// If the battery is full and it's charging, shows the high level
			// notify.
			if (batteryPercentage >= properties.getPropertyAsInteger(BATTERY_FULL_PERCENTAGE_THRESHOLD_KEY)
					&& acStatus.equals(ChargingStatus.ONLINE)) {
				showWindow(batteryPercentage, lifeTime, NotificationType.BATTERY_FULL);
				playNotification(properties.getProperty(BATTERY_FULL_NOTIF_AUDIO_KEY));
			}

			// Sleeps before the next iteration.
			try {
				Thread.sleep(properties.getPropertyAsLong(POLLING_FREQUENCY_KEY));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads this app configuration.
	 */
	private static void loadAppConfig() {

		// Gets the program folder on this machine.
		String programFolder = System.getenv("ProgramFiles") + File.separator + APP_NAME + File.separator;

		// If this is the first time running there won't be a property file. In
		// that case, this copies the default one on the FS, so the user knows
		// what he can customize.
		File config = new File(programFolder, CONFIG_FILE_NAME);
		if (!config.exists()) {
			// If the file directory structure doesn't exist, creates it.
			File parentFile = config.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}

			// Copies the file on FS.
			try {
				Files.copy(Paths.get(Notabat.class.getClassLoader().getResource(CONFIG_FILE_NAME).toURI()),
						Paths.get(programFolder, CONFIG_FILE_NAME));
			} catch (IOException | URISyntaxException e) {
				System.out.println("Error while copying the configuration file [" + CONFIG_FILE_NAME + "] to path ["
						+ programFolder + CONFIG_FILE_NAME + "]");
				e.printStackTrace();
			}
		}

		// Now loads the actual config.
		try {
			properties = new PropertiesController(config);
		} catch (IOException e) {
			System.out.println("Error while loading the configuration.");
			e.printStackTrace();
		}
	}

	/**
	 * Shows the notify window.
	 *
	 * @param batteryPercentage
	 *            the battery level.
	 * @param batteryLifeTime
	 *            the the battery life time. This may be null if the battery is
	 *            charging.
	 * @param notify
	 *            the notification to show.
	 */
	private static void showWindow(final int batteryPercentage, final int batteryLifeTime,
			final NotificationType notify) {
		// Create the GUI on the event-dispatching thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AlertWindow.showWindow(batteryPercentage, batteryLifeTime, notify);
			}
		});
	}

	/**
	 * Plays a notification sound from a file.
	 * 
	 * @param filename
	 *            the file name.
	 */
	private static void playNotification(String filename) {
		if (filename != null && !filename.isEmpty()) {
			// Loads the file.
			File file = new File(filename);
			AudioInputStream audioIn = null;
			try {
				audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
			} catch (IOException | UnsupportedAudioFileException e) {
				System.out.println("Error while loading the notification file.");
				e.printStackTrace();
			}

			// Gets a sound clip resource.
			Clip clip = null;
			try {
				clip = AudioSystem.getClip();
				clip.open(audioIn);
			} catch (LineUnavailableException | IOException e) {
				System.out.println("Error while playing the notification audio.");
				e.printStackTrace();
			}
			// Opens the audio clip.
			clip.start();
		}
	}
}

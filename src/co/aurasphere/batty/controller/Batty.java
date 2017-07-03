package co.aurasphere.batty.controller;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;

import co.aurasphere.batty.utils.Enums.ChargingStatus;
import co.aurasphere.batty.utils.Enums.NotificationType;
import co.aurasphere.batty.window.AlertWindow;

/**
 * @author Donato Rimenti
 *
 */
public class Batty {

	// Checks the status of the battery every three minutes.
	private static final long POLLING_FREQUENCY = 180000;

	private static final int BATTERY_CRITICAL_PERCENTAGE_LEVEL = 20;

	private static final int BATTERY_FULL_PERCENTAGE_LEVEL = 98;

	public static void main(String[] args) {
		// Determine what the GraphicsDevice can support.
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		// If shaped windows aren't supported, exit.
		if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
			System.err.println("Shaped windows are not supported");
			System.exit(0);
		}
		Kernel32.SYSTEM_POWER_STATUS batteryStatus = new Kernel32.SYSTEM_POWER_STATUS();

		for (;;) {

			// Checks if the battery is low.
			Kernel32.INSTANCE.GetSystemPowerStatus(batteryStatus);
			int batteryPercentage = batteryStatus.getBatteryLifePercent();
			if (batteryPercentage <= BATTERY_CRITICAL_PERCENTAGE_LEVEL
					&& !batteryStatus.getACLineStatusString().equals(
							ChargingStatus.ONLINE)) {
				showWindow(batteryPercentage,
						batteryStatus.getBatteryLifeTime() / 60,
						NotificationType.BATTERY_LOW);
				playNotification("battery_low_notification.wav");
			}

			// Checks if the battery is full.
			if (batteryPercentage >= BATTERY_FULL_PERCENTAGE_LEVEL
					&& batteryStatus.getACLineStatusString().equals(
							ChargingStatus.ONLINE)) {
				showWindow(batteryPercentage,
						batteryStatus.getBatteryLifeTime() / 60,
						NotificationType.BATTERY_FULL);
				playNotification("battery_full_notification.wav");
			}

			try {
				Thread.sleep(POLLING_FREQUENCY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void showWindow(final int batteryPercentage,
			final int batteryLifeTime, final NotificationType notify) {
		// Create the GUI on the event-dispatching thread
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AlertWindow.showWindow(batteryPercentage, batteryLifeTime,
						notify);
			}
		});
	}

	private static void playNotification(String filename) {

		// Plays a sound.
		// Open an audio input stream.
		try {
			URL url = Batty.class.getClassLoader().getResource(
					"co/aurasphere/batty/resources/" + filename);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio
			// input stream.
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

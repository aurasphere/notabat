package co.aurasphere.notabat.utils;

/**
 * Enumerations used by this program.
 * 
 * @author Donato Rimenti
 *
 */
public class Enums {

	/**
	 * The charging status of the battery.
	 */
	public enum ChargingStatus {

		/**
		 * The online.
		 */
		ONLINE,
		/**
		 * The offline.
		 */
		OFFLINE,
		/**
		 * The unknown.
		 */
		UNKNOWN;
	}

	/**
	 * The battery charging level.
	 */
	public enum BatteryLevel {

		/**
		 * The high.
		 */
		HIGH,
		/**
		 * The low.
		 */
		LOW,
		/**
		 * The critical.
		 */
		CRITICAL,
		/**
		 * The charging.
		 */
		CHARGING,
		/**
		 * The no battery.
		 */
		NO_BATTERY,
		/**
		 * The unknown.
		 */
		UNKNOWN;
	}

	/**
	 * The type of notification to show.
	 */
	public enum NotificationType {

		/**
		 * The battery low.
		 */
		BATTERY_LOW("Battery low", "Plug in the charger."),
		/**
		 * The battery full.
		 */
		BATTERY_FULL("Battery full", "Unplug the charger.");

		/**
		 * Message to be shown in the alert.
		 */
		private String message;

		/**
		 * Sub message to be shown in the alert.
		 */
		private String subMessage;

		/**
		 * Instantiates a new NotificationType.
		 *
		 * @param message
		 *            the {@link #message}.
		 * @param subMessage
		 *            the {@link #subMessage}.
		 */
		private NotificationType(String message, String subMessage) {
			this.message = message;
			this.subMessage = subMessage;
		}

		/**
		 * Gets the {@link #message}.
		 *
		 * @return the {@link #message}.
		 */
		public String getMessage() {
			return this.message;
		}

		/**
		 * Gets the {@link #subMessage}.
		 *
		 * @return the {@link #subMessage}.
		 */
		public String getSubMessage() {
			return this.subMessage;
		}
	}

}

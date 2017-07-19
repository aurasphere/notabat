package co.aurasphere.notabat.utils;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import co.aurasphere.notabat.utils.Enums.BatteryLevel;
import co.aurasphere.notabat.utils.Enums.ChargingStatus;

/**
 * Interface for reading the battery status from Windows OS (both 32 or 64
 * bits).
 * 
 * @author Donato Rimenti
 *
 */
public interface Kernel32 extends StdCallLibrary {

	/**
	 * Loads the native library.
	 */
	public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

	/**
	 * Structure of Windows OS power data.
	 * 
	 * @see http://msdn2.microsoft.com/en-us/library/aa373232.aspx
	 */
	public class SYSTEM_POWER_STATUS extends Structure {

		/**
		 * The AC power status.
		 */
		public byte ACLineStatus;

		/**
		 * The battery charge status
		 */
		public byte BatteryFlag;

		/**
		 * The percentage of full battery charge remaining. This member can be a
		 * value in the range 0 to 100, or 255 if status is unknown.
		 */
		public byte BatteryLifePercent;

		/**
		 * The status of battery saver. 0 means off, 1 means on.
		 */
		public byte Reserved1;

		/**
		 * The number of seconds of battery life remaining, or –1 if remaining
		 * seconds are unknown or if the device is connected to AC power.
		 */
		public int BatteryLifeTime;

		/**
		 * The number of seconds of battery life when at full charge, or –1 if
		 * full battery lifetime is unknown or if the device is connected to AC
		 * power.
		 */
		public int BatteryFullLifeTime;

		/**
		 * Instantiates a new SYSTEM_POWER_STATUS.
		 */
		public SYSTEM_POWER_STATUS() {
			setAlignType(ALIGN_MSVC);
		}

		/**
		 * Used to map the data retrieved from the OS in order.
		 *
		 * @return a list of fields representing the order of flags sent by OS.
		 */
		@Override
		protected List<String> getFieldOrder() {
			ArrayList<String> fields = new ArrayList<String>();
			fields.add("ACLineStatus");
			fields.add("BatteryFlag");
			fields.add("BatteryLifePercent");
			fields.add("Reserved1");
			fields.add("BatteryLifeTime");
			fields.add("BatteryFullLifeTime");
			return fields;

		}

		/**
		 * The AC power status.
		 */
		public ChargingStatus getACLineStatusString() {
			switch (ACLineStatus) {
			case (0):
				return ChargingStatus.OFFLINE;
			case (1):
				return ChargingStatus.ONLINE;
			default:
				return ChargingStatus.UNKNOWN;
			}
		}

		/**
		 * The battery charge status.
		 */
		public BatteryLevel getBatteryFlagString() {
			switch (BatteryFlag) {
			// More than 66%.
			case (1):
				return BatteryLevel.HIGH;
			// Less than 33%.
			case (2):
				return BatteryLevel.LOW;
			// Less than 5%.
			case (4):
				return BatteryLevel.CRITICAL;
			case (8):
				return BatteryLevel.CHARGING;
			case ((byte) 128):
				return BatteryLevel.NO_BATTERY;
			// Unknown status—unable to read the battery flag information.
			default:
				return BatteryLevel.UNKNOWN;
			}
		}

		/**
		 * The percentage of full battery charge remaining
		 */
		public int getBatteryLifePercent() {
			return (BatteryLifePercent == (byte) 255) ? -1 : BatteryLifePercent;
		}

		/**
		 * The number of seconds of battery life remaining
		 */
		public int getBatteryLifeTime() {
			return (BatteryLifeTime == -1) ? -1 : BatteryLifeTime;
		}

		/**
		 * The number of seconds of battery life when at full charge
		 */
		public int getBatteryFullLifeTime() {
			return (BatteryFullLifeTime == -1) ? -1 : BatteryFullLifeTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sun.jna.Structure#toString()
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("ACLineStatus: " + getACLineStatusString() + "\n");
			sb.append("Battery Flag: " + getBatteryFlagString() + "\n");
			sb.append("Battery Life: " + getBatteryLifePercent() + "%\n");
			sb.append("Battery Left: " + getBatteryLifeTime() + " seconds\n");
			sb.append("Battery Full: " + getBatteryFullLifeTime() + " seconds\n");
			return sb.toString();
		}
	}

	/**
	 * Fill the structure.
	 */
	public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
}
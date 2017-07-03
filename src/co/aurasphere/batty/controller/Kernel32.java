package co.aurasphere.batty.controller;

import java.util.ArrayList;
import java.util.List;

import co.aurasphere.batty.utils.Enums.BatteryLevel;
import co.aurasphere.batty.utils.Enums.ChargingStatus;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {

	public Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32",
			Kernel32.class);

	/**
	 * @see http://msdn2.microsoft.com/en-us/library/aa373232.aspx
	 */
	public class SYSTEM_POWER_STATUS extends Structure {
		public byte ACLineStatus;
		public byte BatteryFlag;
		public byte BatteryLifePercent;
		public byte Reserved1;
		public int BatteryLifeTime;
		public int BatteryFullLifeTime;

		public SYSTEM_POWER_STATUS() {
			setAlignType(ALIGN_MSVC);
		}

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
		 * The AC power status
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
		 * The battery charge status
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
			default:
				return BatteryLevel.UNKNOWN;
			}
		}

		/**
		 * The percentage of full battery charge remaining
		 */
		public int getBatteryLifePercent() {
			return (BatteryLifePercent == (byte) 255) ? -1
					: BatteryLifePercent;
		}

		/**
		 * Tvhe number of seconds of battery life remaining
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

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("ACLineStatus: " + getACLineStatusString() + "\n");
			sb.append("Battery Flag: " + getBatteryFlagString() + "\n");
			sb.append("Battery Life: " + getBatteryLifePercent() + "%\n");
			sb.append("Battery Left: " + getBatteryLifeTime() + " seconds\n");
			sb.append("Battery Full: " + getBatteryFullLifeTime()
					+ " seconds\n");
			return sb.toString();
		}
	}

	/**
	 * Fill the structure.
	 */
	public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);
}
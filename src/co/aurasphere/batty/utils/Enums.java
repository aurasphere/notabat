package co.aurasphere.batty.utils;

public class Enums {
	
	public enum ChargingStatus{
		ONLINE, OFFLINE, UNKNOWN;
	}
	
	public enum BatteryLevel{
		HIGH, LOW, CRITICAL, CHARGING, NO_BATTERY, UNKNOWN;
	}
	
	public enum NotificationType{
		BATTERY_LOW, BATTERY_FULL;
	}

}

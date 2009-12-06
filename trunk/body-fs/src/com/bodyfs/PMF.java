package com.bodyfs;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.compass.core.Compass;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.gps.CompassGps;
import org.compass.gps.device.jdo.Jdo2GpsDevice;
import org.compass.gps.impl.SingleCompassGps;

public class PMF {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private static Compass compass;

	public static Compass getCompass() {
		return compass;
	}

	public static void setCompass(Compass compass) {
		PMF.compass = compass;
	}

	public static CompassGps getCompassGps() {
		return compassGps;
	}

	public static void setCompassGps(CompassGps compassGps) {
		PMF.compassGps = compassGps;
	}

	private static CompassGps compassGps;

	static {
		compass = new CompassConfiguration().setConnection("gae://index").setSetting(
				CompassEnvironment.ExecutorManager.EXECUTOR_MANAGER_TYPE, "disabled").addScan("com.bodyfs.model")
				.buildCompass();

		compassGps = new SingleCompassGps(compass);
		compassGps.addGpsDevice(new Jdo2GpsDevice("appengine", pmfInstance));
		compassGps.start();

		compassGps.index();
	}

	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}

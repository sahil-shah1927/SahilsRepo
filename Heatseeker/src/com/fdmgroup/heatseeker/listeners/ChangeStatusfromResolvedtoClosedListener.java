package com.fdmgroup.heatseeker.listeners;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A listener that scan issues 
 *
 * @author Lei Lin
 *
 */
public class ChangeStatusfromResolvedtoClosedListener implements ServletContextListener {

	
	@Override
	public void contextInitialized(ServletContextEvent sce) {		
		Timer timer = new Timer();
		TimerTask timertask = new StatusTimerTask();
		timer.schedule(timertask, 10000, (2 * 10000));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	//	System.out.println("CloseAuction Listener has been shutdown.");

	}

	

}

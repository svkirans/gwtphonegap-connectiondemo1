package com.codingsquare.cordova.connection.client;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.gwtphonegap.client.connection.Connection;
import com.googlecode.gwtphonegap.client.event.OffLineEvent;
import com.googlecode.gwtphonegap.client.event.OffLineHandler;
import com.googlecode.gwtphonegap.client.event.OnlineEvent;
import com.googlecode.gwtphonegap.client.event.OnlineHandler;
import com.googlecode.mgwt.ui.client.widget.list.celllist.BasicCell;
import com.googlecode.mgwt.ui.client.widget.list.celllist.CellList;
import com.googlecode.mgwt.ui.client.widget.panel.scroll.ScrollPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ConnectionDemo_1 implements EntryPoint {
	 private Logger log = Logger.getLogger(getClass().getName());
	 private ScrollPanel scrollPanel = null;
	 private LinkedList<String> list = null;
	 private CellList<String> cellList = null;
	 private Connection connection = null;
	 private String lastKnownStatus = "";

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		 GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

		      @Override
		      public void onUncaughtException(Throwable e) {
		        Window.alert("uncaught: " + e.getLocalizedMessage());
		        Window.alert(e.getMessage());
		        log.log(Level.SEVERE, "uncaught exception", e);
		      }
		    });

		    final PhoneGap phoneGap = GWT.create(PhoneGap.class);

		    phoneGap.addHandler(new PhoneGapAvailableHandler() {

		      @Override
		      public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
		    	    createUI(phoneGap);
		      }
		    });

		    phoneGap.addHandler(new PhoneGapTimeoutHandler() {

		      @Override
		      public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
		        Window.alert("can not load phonegap");

		      }
		    });
		    phoneGap.initializePhoneGap();
	}
	
	private void createUI(final PhoneGap phoneGap) {
		try {
			scrollPanel =  new ScrollPanel();
			list = new LinkedList<String>();
			BasicCell<String> cell = new BasicCell<String>() {

				@Override
				public String getDisplayString(String model) {
					return model;
				}
			};
			 cellList = new CellList<String>(cell);
			scrollPanel.add(cellList);
			 connection = phoneGap.getConnection();
			addInfo("Connection Type : ");
			phoneGap.getEvent().getOnlineHandler().addOnlineHandler(new OnlineHandler() {
				
				@Override
				public void onOnlineEvent(OnlineEvent event) {
					addInfo("online and connection Type : ");
				}
			});
			phoneGap.getEvent().getOffLineHandler().addOfflineHandler(new OffLineHandler() {
				
				@Override
				public void onOffLine(OffLineEvent event) {
					addInfo("Offline : ");
				}
			});
			RootPanel.get("ConnectionDemo").add(scrollPanel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addInfo(String type) {
		if(connection.getType().equalsIgnoreCase(lastKnownStatus)){
			
		}else{
		list.add(type+connection.getType());
		cellList.render(list);
		scrollPanel.refresh();
		lastKnownStatus = connection.getType();
	}}
}

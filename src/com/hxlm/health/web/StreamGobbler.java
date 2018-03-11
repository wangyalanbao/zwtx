package com.hxlm.health.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private InputStream is;
	private String type;

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				logger.debug("Thread:" + this.getId() + "; name:" + this.getName() + "; " + type + ">" + line);
		} catch (IOException ioe) {
			logger.error("Failed to StreamGobbler! errors:" + ioe.getMessage());				
		}
	}

}

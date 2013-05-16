/*
 * StopWatchLogger.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

import com.google.common.base.Stopwatch;

/**
 * <p>
 * A simple utility for logging the elapsed time since the stop watch timer has
 * started
 * </p>
 * 
 * @author jramoyo
 */
public final class StopWatchLogger {

	private static final String FORMAT = "%s - Time Elapsed: %d ms.";

	private final Logger logger;
	private final Stopwatch stopwatch;

	public StopWatchLogger(Logger logger) {
		this.logger = logger;
		this.stopwatch = new Stopwatch();
	}

	/**
	 * Starts the stop watch
	 */
	public void start() {
		stopwatch.start();
	}

	/**
	 * Stops the stop watch
	 */
	public void stop() {
		stopwatch.stop();
	}

	/**
	 * <p>
	 * Logs a TRACE message and appends the elapsed time since the stop watch
	 * has started
	 * </p>
	 * <p>
	 * The message will be logged in the following format:
	 * 
	 * <pre>
	 * [MESSAGE] - Time Elapsed: [ELAPSED] ms.
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param message
	 *            a message to log
	 */
	public void trace(String message) {
		logger.trace(String.format(FORMAT, message, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
	}

	/**
	 * <p>
	 * Logs a DEBUG message and appends the elapsed time since the stop watch
	 * has started
	 * </p>
	 * <p>
	 * The message will be logged in the following format:
	 * 
	 * <pre>
	 * [MESSAGE] - Time Elapsed: [ELAPSED] ms.
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param message
	 *            a message to log
	 */
	public void debug(String message) {
		logger.debug(String.format(FORMAT, message, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
	}

	/**
	 * <p>
	 * Logs an INFO message and appends the elapsed time since the stop watch
	 * has started
	 * </p>
	 * <p>
	 * The message will be logged in the following format:
	 * 
	 * <pre>
	 * [MESSAGE] - Time Elapsed: [ELAPSED] ms.
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param message
	 *            a message log
	 */
	public void info(String message) {
		logger.info(String.format(FORMAT, message, stopwatch.elapsed(TimeUnit.MILLISECONDS)));
	}
}
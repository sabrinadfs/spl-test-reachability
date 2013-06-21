package main;

import antenna.preprocessor.v3.ILogger;

public class LoggerImpl implements ILogger {

	@Override
	public void log(String message) {
		System.out.println("LOG: " + message);
	}

}

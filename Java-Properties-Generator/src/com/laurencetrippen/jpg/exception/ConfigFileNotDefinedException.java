package com.laurencetrippen.jpg.exception;

public class ConfigFileNotDefinedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "(@ConfigFile annotation is not defined)";
	}
}
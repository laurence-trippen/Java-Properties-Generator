package com.laurencetrippen.jpg.exception;

public class ConfigFileAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public ConfigFileAlreadyExistException(String path) {
		this.path = path;
	}
	
	@Override
	public String getMessage() {
		return path + " already exists!";
	}
}
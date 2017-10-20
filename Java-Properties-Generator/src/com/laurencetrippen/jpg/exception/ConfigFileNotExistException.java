package com.laurencetrippen.jpg.exception;

public class ConfigFileNotExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public ConfigFileNotExistException(String path) {
		this.path = path;
	}
	
	@Override
	public String getMessage() {
		return path + " file not exists!";
	}
}
package com.laurencetrippen.jpg.exception;

public class PathNotExistException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public PathNotExistException(String path) {
		this.path = path;
	}
	
	@Override
	public String getMessage() {
		return path + " path not exists!";
	}
}
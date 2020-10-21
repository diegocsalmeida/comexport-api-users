package br.net.comexport.api.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException (String message) {
		super(message);
	}
}

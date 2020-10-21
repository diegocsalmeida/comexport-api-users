package br.net.comexport.api.service.exceptions;

public class ResourceAlredyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ResourceAlredyExistsException (String message) {
		super(message);
	}
}

package br.net.comexport.api.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.net.comexport.api.service.exceptions.ApiExceptionDetail;
import br.net.comexport.api.service.exceptions.ResourceAlredyExistsException;
import br.net.comexport.api.service.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiExceptionDetail> handleResourceNotFoundException(ResourceNotFoundException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiExceptionDetail exceptionDetail = new ApiExceptionDetail(status.value(), e.getMessage());

		return ResponseEntity.status(status).body(exceptionDetail);
	}

	@ExceptionHandler(ResourceAlredyExistsException.class)
	public ResponseEntity<ApiExceptionDetail> handleResourceNotFoundException(ResourceAlredyExistsException e,
			HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ApiExceptionDetail exceptionDetail = new ApiExceptionDetail(status.value(), e.getMessage());

		return ResponseEntity.status(status).body(exceptionDetail);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiExceptionDetail exceptionDetail = new ApiExceptionDetail(status.value(), "One or more invalid fields. Check and try again.");
		List<ApiExceptionDetail.Field> fields = new ArrayList<ApiExceptionDetail.Field>();
		
		for(ObjectError error :ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			
			fields.add(new ApiExceptionDetail.Field(fieldName, message));
		}
		
		exceptionDetail.setFields(fields);

		return super.handleExceptionInternal(ex, exceptionDetail, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		ApiExceptionDetail exceptionDetail = new ApiExceptionDetail(status.value(), ex.getMessage());
		
		return super.handleExceptionInternal(ex, exceptionDetail, headers, status, request);
	}
}

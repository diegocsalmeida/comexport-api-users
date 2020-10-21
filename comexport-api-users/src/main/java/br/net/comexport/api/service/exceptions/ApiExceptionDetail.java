package br.net.comexport.api.service.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiExceptionDetail {

	private int status;
	private String message;
	private LocalDateTime timestamp;
	
	@JsonInclude(Include.NON_EMPTY)
	private List<Field> fields;

	public ApiExceptionDetail(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public static class Field {
		private String fieldName;
		private String message;
		
		public Field(String fieldName, String message) {
			super();
			this.fieldName = fieldName;
			this.message = message;
		}
		public String getMensagem() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
	}


}
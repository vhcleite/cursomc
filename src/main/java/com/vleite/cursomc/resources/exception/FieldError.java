package com.vleite.cursomc.resources.exception;

public class FieldError {

	private String field;
	private String Message;

	public FieldError(String field, String message) {
		super();
		this.field = field;
		Message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}

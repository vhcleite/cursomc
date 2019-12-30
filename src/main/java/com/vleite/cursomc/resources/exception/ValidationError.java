package com.vleite.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldError> errors = new ArrayList<>();

	public ValidationError(Integer status, String message, Long timestamp) {
		super(status, message, timestamp);
	}

	public void addError(String field, String message) {
		errors.add(new FieldError(field, message));
	}

	public List<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}

}

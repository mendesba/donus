package com.ztech.donus.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.util.Utils;

import lombok.NoArgsConstructor;


@NoArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final List<String> errors = new ArrayList<>();
		final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.sorted(Comparator.comparing(FieldError::getDefaultMessage)).collect(Collectors.toList());
		for (final FieldError error : fieldErrors) {
			errors.add(Utils.getMessageValidation(error.getDefaultMessage()));
		}
		final List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors().stream()
				.sorted(Comparator.comparing(ObjectError::getDefaultMessage)).collect(Collectors.toList());
		for (final ObjectError error : objectErrors) {
			errors.add(error.getDefaultMessage());
		}
		ErrorDetails error = ErrorDetails.builder().timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value()).title("Field Validation Exception").details(errors)
				.className(ex.getClass().getName()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorDetails error = ErrorDetails.builder().timestamp(new Date().getTime()).status(status.value())
				.title("Internal Error").details(Arrays.asList(ex.getLocalizedMessage()))
				.className(ex.getClass().getName()).build();
		return new ResponseEntity<>(error, status);
	}

	@ExceptionHandler({ BusinessException.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		ErrorDetails error = ErrorDetails.builder().timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value()).title("Internal Error").details(Arrays.asList(ex.getMessage()))
				.className(ex.getClass().getName()).build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
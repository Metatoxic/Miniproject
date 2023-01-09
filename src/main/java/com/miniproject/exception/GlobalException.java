package com.miniproject.exception;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Map<String, Object>> exception(Exception exception) {

		Map<String, Object> mapException = new HashMap<String, Object>();
		mapException.put("timeStamp", LocalDate.now());

		mapException.put("errorMessage", exception.getMessage());

		return new ResponseEntity<Map<String, Object>>(mapException, HttpStatus.CONFLICT);
	}

}

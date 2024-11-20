package org.iffomko.server.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.iffomko.server.exceptions.LocalizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LocalizedExceptionControllerAdvice {

    @ExceptionHandler(value = LocalizedException.class)
    public ResponseEntity<Map<String, String>> handleLocalizedException(LocalizedException ex, HttpServletRequest request) {
        Map<String, String> errorView = new HashMap<>();
        errorView.put("message", ex.getMessage());
        errorView.put("timestamp", Instant.now().toString());
        errorView.put("path", request.getRequestURI());
        return ResponseEntity.badRequest().body(errorView);
    }
}

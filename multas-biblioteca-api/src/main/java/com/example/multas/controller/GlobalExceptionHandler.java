package com.example.multas.controller;

import com.example.multas.domain.PagoRechazadoException;
import com.example.multas.model.LimiteMultasPendientesException;
import com.example.multas.model.MultaNotFoundException;
import com.example.multas.model.MultaYaPagadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultaNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(MultaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(LimiteMultasPendientesException.class)
    public ResponseEntity<Map<String, String>> handleLimiteSuperado(LimiteMultasPendientesException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MultaYaPagadaException.class)
    public ResponseEntity<Map<String, String>> handleYaPagada(MultaYaPagadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", ex.getMessage()));
    }

    // NUEVO MANEJADOR: Captura el rechazo de la pasarela de pagos
    @ExceptionHandler(PagoRechazadoException.class)
    public ResponseEntity<Map<String, String>> handlePagoRechazado(PagoRechazadoException ex) {
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errores.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
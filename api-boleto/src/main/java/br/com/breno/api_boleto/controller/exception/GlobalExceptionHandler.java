package br.com.breno.api_boleto.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> applicationException(ApplicationException applicationException, WebRequest webRequest) {
        var response = ErroResponse.builder()
                .erro(applicationException.getMessage())
                .codigo(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .path(webRequest.getDescription(false)).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException notFoundException, WebRequest webRequest) {
        var response = ErroResponse.builder()
                .erro(notFoundException.getMessage())
                .codigo(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .path(webRequest.getDescription(false)).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {

        var erros = methodArgumentNotValidException.getFieldErrors()
                .stream()
                .map(item -> item.getField() + " " + item.getDefaultMessage())
                .collect(Collectors.joining());

        var response = ErroResponse.builder()
                .erro(erros)
                .codigo(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .path(webRequest.getDescription(false)).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}

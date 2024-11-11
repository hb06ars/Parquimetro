package com.parquimetro.infra.exceptions;

import com.parquimetro.domain.dto.MessageError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ListErrorResponse> handleErrorSaveObjectException(Exception e) {
        ListErrorResponse ListErrorResponse = new ListErrorResponse(
                List.of(MessageError.builder()
                        .detalhe(e.getMessage())
                        .erro("Erro no sistema")
                        .build()),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(ListErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ListErrorResponse> handleValidationException(WebExchangeBindException ex) {
        List<MessageError> errorMessages = ex.getFieldErrors().stream()
                .map(error ->
                        MessageError.builder()
                                .detalhe(String.format("Campo: %s. %s", error.getField(), error.getDefaultMessage()))
                                .erro(error.getDefaultMessage())
                                .build())
                .collect(Collectors.toList());

        ListErrorResponse ListErrorResponse = new ListErrorResponse(
                errorMessages,
                HttpStatus.BAD_REQUEST.value() 
        );
        return new ResponseEntity<>(ListErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ListErrorResponse> handleObjectNotFoundException(ObjectNotFoundException e) {
        ListErrorResponse ListErrorResponse = new ListErrorResponse(
                List.of(MessageError.builder()
                        .detalhe(e.getMessage())
                        .erro("O objeto solicitado não foi encontrado no sistema")
                        .build()),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(ListErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ListErrorResponse> handlePassengerAlreadyExistsdException(DataIntegrityViolationException e) {
        ListErrorResponse ListErrorResponse = new ListErrorResponse(
                List.of(MessageError.builder()
                        .detalhe(e.getMessage())
                        .erro("O registro já existe no sistema com os dados informados (CPF / Email / Passaporte).")
                        .build()),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(ListErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ListErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ListErrorResponse ListErrorResponse = new ListErrorResponse(
                List.of(MessageError.builder()
                        .erro("Erro no sistema.")
                        .detalhe("Requisição inválida, preencha corretamente.")
                        .build()),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(ListErrorResponse, HttpStatus.BAD_REQUEST);
    }


}

package org.evan.project.fault;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.evan.project.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            HttpServletRequest request
    ) {
        return buildResponse(
                ObsFault.RESOURCE_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            HttpServletRequest request
    ) {
        return buildResponse(
                ObsFault.INVALID_INPUT,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleStock(
            HttpServletRequest request
    ) {
        return buildResponse(
                ObsFault.INSUFFICIENT_STOCK,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("Unhandled exception", ex);

        return buildResponse(
                ObsFault.GENERAL_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            ObsFault fault,
            HttpStatus status,
            String path
    ) {
        String time = ZonedDateTime.now()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        ErrorResponse body = new ErrorResponse(
                fault.getCode(),
                fault.getDefaultMessage(),
                path,
                time
        );

        return ResponseEntity.status(status).body(body);
    }
}

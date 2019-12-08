package com.store.handler;


import com.store.dto.ResponseDTO;
import com.store.exception.StoreBusinessException;
import com.store.exception.StoreGenericException;
import com.store.exception.StoreNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest req, Exception ex) {
        log.error("Exception interceptada: " + req.getRequestURL() + " ocasionada  " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(StoreGenericException.class)
    public ResponseEntity<Object> handleStoreGenericException(HttpServletRequest req, Exception ex) {
        log.error("Exception interceptada: " + req.getRequestURL() + " ocasionada  " + ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(StoreBusinessException.class)
    public ResponseEntity<Object> handleStoreBusinessException(HttpServletRequest req, Exception ex) {
        log.warn("Exception interceptada: " + req.getRequestURL() + " ocasionada  " + ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseDTO(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<Object> handleStoreRegisterNotFoundException(HttpServletRequest req, Exception ex) {
        log.warn("Exception interceptada: " + req.getRequestURL() + " ocasionada  " + ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(ex.getMessage()));
    }

}

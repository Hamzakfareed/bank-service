package com.whitebox.accounts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerHelper {

    public static <T>ResponseEntity<T> prepareResponse(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public static ResponseEntity prepareErrorResponse(String message) {
        return ResponseEntity.badRequest().body(message);
    }
}

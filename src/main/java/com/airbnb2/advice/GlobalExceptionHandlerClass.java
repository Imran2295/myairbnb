package com.airbnb2.advice;

import com.airbnb2.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandlerClass {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RecordNotFoundException.class)
    public Map<String , String> handleRecordNotFoundException(RecordNotFoundException e , WebRequest web){

        Map<String , String> map = new HashMap<>();
        map.put("Error Message" , e.getMessage());
        map.put("Details" , web.getDescription(false));
        map.put("Date" , String.valueOf(new Date()));
        map.put("Status" , HttpStatus.NOT_FOUND.getReasonPhrase());

        return map;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String , String> handleALlException(Exception e , WebRequest web){

        Map<String , String> map = new HashMap<>();
        map.put("Error Message" , e.getMessage());
        map.put("Details" , web.getDescription(true));
        map.put("Date" , String.valueOf(new Date()));
        map.put("Status" , HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return map;
    }
}

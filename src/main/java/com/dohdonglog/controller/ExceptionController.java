package com.dohdonglog.controller;

import com.dohdonglog.exception.InvalidRequest;
import com.dohdonglog.exception.PostNotFound;
import com.dohdonglog.exception.dohdonglogException;
import com.dohdonglog.response.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){

       ErrorResponse response = ErrorResponse.builder()
               .code("400")
               .message("잘못된 요청입니다.")
               .build();

       for (FieldError fieldError : e.getFieldErrors()) {
           response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());

       }
       return response;

    }

    @ResponseBody
    @ExceptionHandler(dohdonglogException.class)
    public ResponseEntity<ErrorResponse> dohdonglogException(dohdonglogException e){

        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        // 응답 json validation -> title : 제목에 '바보'를 추가할 수 없습니다.
//        if(e instanceof InvalidRequest){
//            InvalidRequest invalidRequest = (InvalidRequest) e;
//            String fieldName = invalidRequest.getFieldName();
//            String message = invalidRequest.getMessage();
//
//            body.addValidation(fieldName, message);
//        }


        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }



}

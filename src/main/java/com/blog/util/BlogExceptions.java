//package com.blog.util;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.IncorrectResultSizeDataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//public class BlogExceptions extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = {DataAccessException.class})
//    public ResponseEntity<?> handleJDBCExceptions(DataAccessException exception){
//        String errorMassage = exception.getLocalizedMessage();
//        if(errorMassage == null){
//            errorMassage = "Cannot perform operation";
//        }
//        return new ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(value = {IncorrectResultSizeDataAccessException.class})
//    public ResponseEntity<?> handleJDBCQueryException(IncorrectResultSizeDataAccessException exception){
//        String errorMassage = exception.getLocalizedMessage();
//        if(errorMassage == null){
//            errorMassage = "Cannot perform query operation";
//        }
//        return new ResponseEntity(errorMassage, HttpStatus.BAD_REQUEST);
//    }
//}

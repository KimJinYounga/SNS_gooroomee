package com.gooroomee.api.error;

import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.error.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundMember(MemberNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundBoard(BoardNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
}

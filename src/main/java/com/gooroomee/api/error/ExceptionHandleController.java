package com.gooroomee.api.error;

import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.error.exception.CommentsNotFoundException;
import com.gooroomee.api.error.exception.PostNotFoundException;
import com.gooroomee.api.error.exception.MemberNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandleController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandleController.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity handleException(Throwable throwable) {
        System.out.println("logging!!!!!!!internal server error@@@@@@==================================");
        logging(throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundMember(MemberNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundPost(PostNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundBoard(BoardNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(CommentsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundComment(CommentsNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotSupprotException(HttpRequestMethodNotSupportedException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND,exception);
    }

    protected void logging(Throwable throwable) {
        System.out.println("logging!!!!!!!==================================");
        if (logger.isErrorEnabled()) {
            if (throwable.getMessage() != null) {
                logger.error(throwable.getMessage(), throwable);
            } else {
                logger.error("ERROR", throwable);
            }
        }
    }
}

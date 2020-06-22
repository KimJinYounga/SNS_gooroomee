package com.gooroomee.api.error;

import com.gooroomee.api.error.exception.MemberNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse{
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;

    public ErrorResponse(HttpStatus httpStatus,Exception e){
        this.timestamp= LocalDateTime.now();
        this.status=httpStatus.value();
        this.error=httpStatus.toString();
        this.message=e.getMessage();
    }
}

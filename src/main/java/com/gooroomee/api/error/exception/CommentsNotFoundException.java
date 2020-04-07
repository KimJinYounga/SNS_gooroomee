package com.gooroomee.api.error.exception;

public class CommentsNotFoundException extends RuntimeException{
    public CommentsNotFoundException(String msg, Throwable t){
        super(msg,t);
    }
    public CommentsNotFoundException(String msg){
        super(msg);
    }
    public CommentsNotFoundException(){
        super();
    }
}

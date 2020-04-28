package com.gooroomee.api.error.exception;

public class PostDeletedException extends RuntimeException{
    public PostDeletedException(String msg, Throwable t){
        super(msg,t);
    }
    public PostDeletedException(String msg){
        super(msg);
    }
    public PostDeletedException(){
        super();
    }
}

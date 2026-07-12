package com.landconnect.exception;

public class LandNotFoundException  extends RuntimeException{
    public LandNotFoundException(String message){
        super(message);
    }
}

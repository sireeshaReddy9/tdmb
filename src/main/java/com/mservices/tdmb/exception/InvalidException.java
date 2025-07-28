package com.mservices.tdmb.exception;

import org.apache.logging.log4j.message.Message;

public class InvalidException extends RuntimeException{

    public InvalidException(String Message){
        super(Message);
    }
}

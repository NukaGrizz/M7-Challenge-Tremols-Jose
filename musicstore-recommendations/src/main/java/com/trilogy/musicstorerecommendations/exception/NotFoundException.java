package com.trilogy.musicstorerecommendations.exception;

//Code taken from class

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}

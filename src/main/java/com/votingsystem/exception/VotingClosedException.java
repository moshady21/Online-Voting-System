package com.votingsystem.exception;

public class VotingClosedException extends RuntimeException {
    public VotingClosedException(String message) {
        super(message);
    }
}

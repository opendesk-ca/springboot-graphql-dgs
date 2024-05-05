package com.accounts.exceptions;

public class TransactionExistsException extends RuntimeException{
    public TransactionExistsException(String message) {
        super(message);
    }
}

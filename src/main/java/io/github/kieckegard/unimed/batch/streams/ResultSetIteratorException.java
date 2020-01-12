/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.unimed.batch.streams;

/**
 * A runtime exception that is used to wrap the {@link SQLException} checked exceptions.
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ResultSetIteratorException extends RuntimeException {

    public ResultSetIteratorException(String message) {
        super(message);
    }

    public ResultSetIteratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultSetIteratorException(Throwable cause) {
        super(cause);
    }
}

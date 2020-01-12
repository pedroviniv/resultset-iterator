/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

/**
 * A runtime exception that must wrap {@link SQLException} checked exceptions
 * while processing each ResultSet row.+
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class RowMapperException extends RuntimeException {

    public RowMapperException(String message) {
        super(message);
    }

    public RowMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public RowMapperException(Throwable cause) {
        super(cause);
    }
    
}

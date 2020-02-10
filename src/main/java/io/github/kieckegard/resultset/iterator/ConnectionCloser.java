/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * functional interface that represents a consumer that closes
 * a JDBC connection.
 * 
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
@FunctionalInterface
public interface ConnectionCloser {
    
    /**
     * method that closes a JDBC connection.
     * @param connection connection that must be close
     * @throws SQLException if some error occurr while connection closing.
     */
    void close(final Connection connection) throws SQLException;
}

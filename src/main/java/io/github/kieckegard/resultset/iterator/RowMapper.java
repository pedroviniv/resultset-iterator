/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

import java.sql.ResultSet;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public interface RowMapper<T> {
    
    /**
     * should convert the current row of the resultSet into a instance
     * of type T.
     * @param resultSet
     * @return 
     */
    T map(final ResultSet resultSet) throws RowMapperException;
}

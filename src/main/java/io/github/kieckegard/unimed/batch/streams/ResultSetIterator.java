/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.unimed.batch.streams;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * A special implementation of {@link Iterator} which is backed by a ResultSet.
 * Iterating over this iterator causes iterating over the ResultSet. This implementation
 * was built in order to make possible navigate through the ResultSet using
 * the Streams api.
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ResultSetIterator<T> implements Iterator<T> {

    /**
     * The wrapped {@code ResultSet}.
     */
    private final ResultSet rs;
    private final RowMapper<T> rowMapper;

    /**
     * Constructor for ResultSetIterator.
     * @param rs Wrap this {@code ResultSet} in an {@code Iterator}.
     * @param rowMapper A mapper that maps the current entry of the resultSet into
     * a instance of T.
     */
    public ResultSetIterator(final ResultSet rs, final RowMapper rowMapper) {
        this.rs = rs;
        this.rowMapper = rowMapper;
    }

    /**
     * Returns true if there are more rows in the ResultSet.
     * @return boolean {@code true} if there are more rows
     * @throws RuntimeException if an SQLException occurs.
     */
    @Override
    public boolean hasNext() {
        try {
            return !rs.isLast();
        } catch (final SQLException e) {
            rethrow("An error occurred while verifying if it's the last row of the resultSet", e);
            return false;
        }
    }

    /**
     * Returns the next row as an {@code Object[]}.
     * @return An instance of T built using {@link RowMapper} using
     * the next row from the {@link ResultSet}.
     * @see java.util.Iterator#next()
     * @throws RuntimeException if a SQLException occurs.
     */
    @Override
    public T next() {
        try {
            rs.next();
            return this.rowMapper.map(rs);
        } catch (final SQLException e) {
            rethrow("An error occurred while proccessing the next row of ResultSet", e);
            return null;
        }
    }

    /**
     * Deletes the current row from the {@code ResultSet}.
     * @see java.util.Iterator#remove()
     * @throws RuntimeException if an SQLException occurs.
     */
    @Override
    public void remove() {
        try {
            this.rs.deleteRow();
        } catch (final SQLException e) {
            rethrow("An error ocurred while deleting the current row of the resultSet.", e);
        }
    }

    /**
     * Rethrow the SQLException as a RuntimeException.  This implementation
     * creates a new RuntimeException with the SQLException's error message.
     * @param e SQLException to rethrow
     * @since resultset-iterator 1.0
     */
    protected void rethrow(final SQLException e) {
        throw new ResultSetIteratorException(e);
    }
    
    protected void rethrow(final String msg, final SQLException ex) {
        throw new ResultSetIteratorException(msg, ex);
    }

}

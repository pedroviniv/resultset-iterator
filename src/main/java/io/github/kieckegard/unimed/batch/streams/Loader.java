/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.unimed.batch.streams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterators;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Loader {
    
    private static Product of(final ResultSet rs) {
        try {
        return Product.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getBigDecimal("price"))
                .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args) throws SQLException {
        
        final PGSimpleDataSource source = new PGSimpleDataSource();
        source.setUser("postgres");
        source.setPassword("123456");
        source.setDatabaseName("streams");
        source.setServerName("localhost");
        source.setPortNumber(5434);
        
        final Connection conn = source.getConnection();
        PreparedStatement pstm = conn.prepareStatement("SELECT id, name, price FROM product");
        
        ResultSetIterator<Product> resultSetIterator = new ResultSetIterator<>(pstm.executeQuery(), Loader::of);
        
        Stream<Product> stream = Streams.of(resultSetIterator);
        Streams.batched(stream, 2)
                .forEach(System.out::println);
    }
}

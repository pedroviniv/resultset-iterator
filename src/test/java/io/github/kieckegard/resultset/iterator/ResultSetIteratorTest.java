/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class ResultSetIteratorTest {

    private int cursorIndex = -1; 
    private static final List<List<Object>> TABLE = Arrays.asList(
            row(1L, "A", 2000d),
            row(2L, "B", 3000d),
            row(3L, "C", 4000d),
            row(4L, "D", 5000d),
            row(5L, "E", 6000d)
    );

    private static final Integer COLS_NUM = 3;
    private static final Integer ROWS_NUM = TABLE.size();

    private static <T> List<T> row(final T... cols) {
        return Arrays.asList(cols);
    }

    public Boolean next() {
        this.cursorIndex += 1;
        return !this.isLast();
    }

    public Boolean isLast() {

        return this.cursorIndex == this.TABLE.size();
    }

    public <T> T getCellValue(final int resultSetIndex, final Class<T> type) {
        return type.cast(this.TABLE.get(this.cursorIndex).get(resultSetIndex));
    }

    public ResultSet getResultSet() throws SQLException {

        final ResultSet mockedResultSet = Mockito.mock(ResultSet.class);
        this.cursorIndex = -1;

        Mockito.when(mockedResultSet.next()).thenAnswer((iom) -> {
            return this.next();
        });
        Mockito.when(mockedResultSet.isLast()).thenAnswer(iom -> this.isLast());

        IntStream.range(0, COLS_NUM)
                .forEach(index -> {
                    try {
                        Mockito.when(mockedResultSet.getString(index)).thenAnswer(iom -> this.getCellValue(index, String.class));
                        Mockito.when(mockedResultSet.getInt(index)).thenAnswer(iom -> this.getCellValue(index, Integer.class));
                        Mockito.when(mockedResultSet.getLong(index)).thenAnswer(iom -> this.getCellValue(index, Long.class));
                        Mockito.when(mockedResultSet.getDouble(index)).thenAnswer(iom -> this.getCellValue(index, Double.class));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        return mockedResultSet;
    }

    @Test
    public void testNext() throws SQLException {

        final ResultSet resultSet = this.getResultSet();

        final ResultSetIterator<Product> resultSetIterator = new ResultSetIterator<>(resultSet, this::toProduct);

        final Product expProduct = Product.builder().id(1L).name("A").price(BigDecimal.valueOf(2000d))
                .build();

        assertEquals(expProduct, resultSetIterator.next());
    }

    private Product toProduct(final ResultSet resultSet) {
        try {
            return Product.builder()
                    .id(resultSet.getLong(0))
                    .name(resultSet.getString(1))
                    .price(BigDecimal.valueOf(resultSet.getDouble(2)))
                    .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void testHasNext() throws SQLException {
        final ResultSet resultSet = this.getResultSet();

        final ResultSetIterator<Product> resultSetIterator = new ResultSetIterator<>(resultSet, this::toProduct);

        for(int i = 0; i < ROWS_NUM; i++) {
            resultSetIterator.next();
            
            if (i == ROWS_NUM) {
                assertFalse(resultSetIterator.hasNext());
                break;
            }
            
            assertTrue(resultSetIterator.hasNext());
        }
    }

}

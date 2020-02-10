/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.resultset.iterator;

import java.io.Closeable;
import java.util.Iterator;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public interface CloseableIterator<T> extends Iterator<T>, AutoCloseable {
    
}

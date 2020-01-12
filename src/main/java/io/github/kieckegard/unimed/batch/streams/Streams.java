/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.kieckegard.unimed.batch.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Pedro Arthur <pfernandesvasconcelos@gmail.com>
 */
public class Streams {
    
    public static <T> Stream<T> of(final Iterator<T> iterator) {
        
        final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        return StreamSupport.stream(spliterator, false);
    }

    public static <T> Stream<List<T>> batched(Stream<T> s, int batchSize) {

        if (batchSize < 1) {
            throw new IllegalArgumentException("chunkSize==" + batchSize);
        }

        if (batchSize == 1) {
            return s.map(Collections::singletonList);
        }

        final Spliterator<T> src = s.spliterator();

        long size = src.estimateSize();

        if (size != Long.MAX_VALUE) {
            size = (size + batchSize - 1) / batchSize;
        }

        int ch = src.characteristics();

        ch &= Spliterator.SIZED | Spliterator.ORDERED | Spliterator.DISTINCT | Spliterator.IMMUTABLE;
        ch |= Spliterator.NONNULL;

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<List<T>>(size, ch) {
            private List<T> current;

            @Override
            public boolean tryAdvance(Consumer<? super List<T>> action) {
                if (current == null) {
                    current = new ArrayList<>(batchSize);
                }
                while (current.size() < batchSize && src.tryAdvance(current::add));
                if (!current.isEmpty()) {
                    action.accept(current);
                    current = null;
                    return true;
                }
                return false;
            }
        }, s.isParallel());
    }
}

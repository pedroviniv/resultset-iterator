# resultset-iterator

A wrapper of ResultSet in form of an Iterator

# why?

Currently JDBC hasn't native support to Java 8 Streams API and the Streams API has a
simple way to create a Stream from a Iterator using the helper class StreamSupport.

That said, this project aims to deliver a way to iterate a JDBC ResultSet using the Iterator
interface. Doing that, we can simply get a Stream out of it and proccess the data lazily.



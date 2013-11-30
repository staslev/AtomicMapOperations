AtomicMapOperations
===================
A library that provides common operations on concurrent maps, particularly non blocking ones.

It is often non tirival to perform atomic actions on concurrent maps, especially when employing non-blocking paradigms. The *AtomicMapOperations* lib provides the following constructs to abstract some of these difficulties away: <code>putOrTransform</code>, <code>transformIfPresent</code>, <code>putOrAggregate</code>, <code>aggregateIfPresent</code>. 

In case of a map with values of type Long, typically used for counting hits for a given key, instead of the generic constructs mentioned earlier, the lib offers: <code>increase</code> and <code>decrease</code>.

While there may be a certain redundancy when it comes to expressiveness, since transformations can be expressed using aggregations and vice verse, each has its own benefits. Generally speaking, **transformation is best used when the next value is dependent only upon the previous value**, while **aggregations is best used when the next value is dependent both upon the previous value, and an input value**. Using each appropriately can reduce the number of object instances created to carry out an operation. For instance, using a transformation to perform an aggregation may require creating a new transform (i.e., an instance of <code>Transformer</code>) per operation, as opposed to using an aggregator, where a single aggregator instance can be used for multiple operations.

Examples
=======

This will atomically increase the value of the key 'now' in a non blocking manner. If done manually this requires some hoop jumping as no synchronization is used, and multiple thread can change this value simultatiously:
 
    ConcurrentHashMap<Long, Long> hitsPerTimeStamp = new ConcurrentHashMap<Long, Long>();
    final long now = System.currentTimeMillis();
    
    NonBlockingOperations.forMap.withLongValues().increase(hitsPerTimeStamp, now);
    

This will set the key <code>myId</code> to the value of <code>System.currentTimeMillis()</code> regardless of it was present in the map before:

    ConcurrentHashMap<Long, Long> idToLastHitTimestamp = new ConcurrentHashMap<Long, Long>();
    final long myId = 12345L;
    final NonBlockingOperations.Transformer<Long> timestampTransformer = 
     new NonBlockingOperations.Transformer<Long>() {
           @Override
           public Long transform(final Long value) {
               // if our transformation depended on 'value', we'd have to check for nullity
               return System.currentTimeMillis();
           }
       };
    
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(idToLastHitTimestamp, myId, timestampTransformer);
    
This will aggregatively count only even numbers:

    ConcurrentHashMap<Long, Long> id2EvenNumbersCount = new ConcurrentHashMap<Long, Long>();
    final int myInput = 2;
    final Long myId = 12345L;
    
    final NonBlockingOperations.Aggregator<Integer, Long> evenNumbersCounterAggregator = 
     new NonBlockingOperations.Aggregator<Integer, Long>() {
       @Override
       public Long aggregate(final Integer input, final Long previousValue) {
           // when using putOrAggregate, 'previousValue' CAN BE NULL
           // when using aggregateIfPresent, it won't
           long initialValue = previousValue == null ? 0 : previousValue;
           return input % 2 == 0 ? initialValue + 1 : initialValue;
       }
     };

    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(
          id2EvenNumbersCount,
          myId,
          evenNumbersCounterAggregator,
          myInput)
 
    

Binaries
=========
    <dependency>
      <groupId>com.github.staslev</groupId>
      <artifactId>AtomicMapOperations</artifactId>
      <version>1.0</version>
    </dependency>

(hosted by [sonatype](https://oss.sonatype.org/content/repositories/releases/) and [maven central](http://search.maven.org/))

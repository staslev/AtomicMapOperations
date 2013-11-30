AtomicMapOperations
===================
AtomicMapOperations is a library that provides common opertaions on cunncurrent maps, particulary non blocking ones.

It is often non tirival to perform atomic actions on concurrent maps, especially when employing non-blocking paradigms. The *AtomicMapOperations* lib provides the following constructs to abstract some of these difficulties away: <code>putOrTransform</code>, <code>transformIfPresent</code>, <code>putOrAggregate</code>, <code>aggregateIfPresent</code>. 

In case of a map with values of type Long, typically used for counting hits for a given key, instead of the generic constructs mentioned earlier, the lib offers: <code>increase</code> and <code>decrease</code>.

While there may be a certian redundancy when it comes to expressiveness, since transformations can be expressed using aggregations and vice versa, each has its own benefits. Generally speaking, **transformation is best used when the next value is dependent only upon the previous value**, while **aggregaitions is best used when the next value is dependent both upon the previous value, and an input value**. Using each approprietly can reduce the number of object instances created to carry out an operation. For instance, using a transformation to perfrom an aggreation may require creating a new transform (i.e., an instance of <code>Transformer</code>) per opertaion, as opposed to using an aggregator, where a signle aggregator instance can be used for multiple operations.

Examples
=======

This will atomically increase the value of the key 'now' in a non blocking manner. If done manully this requires some hoop jumping as no synchronization is used, and multiple thread can change this value simultatiously:
 
    ConcurrentHashMap<Long, Long> hitsPerTimeStamp = new ConcurrentHashMap<Long, Long>();
    final long now = System.currentTimeMillis();
    
    NonBlockingOperations.forMap.withLongValues().increase(hitsPerTimeStamp, now);
    

This will set the key <code>myId</code> to the value of <code>System.currentTimeMillis()</code> regardless of it was present in th map before:

    ConcurrentHashMap<Long, Long> idToLastHitTimestamp = new ConcurrentHashMap<Long, Long>();
    final long myId = 12345L;
    final NonBlockingOperations.Transformer<Long> timestampTransformer = new NonBlockingOperations.Transformer<Long>() {
          @Override
          public Long transform(final Long value) {
              return System.currentTimeMillis();
          }
      };
    
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(idToLastHitTimestamp, myId, timestampTransformer);
    
 This will aggregatively count only even numbers:  
    
    ConcurrentHashMap<Long, Long> id2EvenNumbersCount = new ConcurrentHashMap<Long, Long>();
    final NonBlockingOperations.Aggregator<Integer, Long> evenNumbersCounterAggregator = new NonBlockingOperations.Aggregator<Integer, Long>() {
      @Override
      public Long aggregate(final Integer input, final Long previousValue) {
        return input % 2 == 0 ? previousValue + 1 : previousValue;
      }
    };
    final int myInput = 2;
    
    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(
        id2EvenNumbersCount, 
        myId, 
        evenNumbersCounterAggregator, 
        myInput);    
    

Binaries
=========
    <dependency>
      <groupId>com.github.staslev</groupId>
      <artifactId>AtomicMapOperations</artifactId>
      <version>1.0</version>
    </dependency>

(hosted by [sonatype](https://oss.sonatype.org/content/repositories/releases/) and [maven central](http://search.maven.org/))

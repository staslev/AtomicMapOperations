AtomicMapOperations
===================
A library that provides basic but common operations on concurrent maps, particularly non blocking ones, without making its clients jump through hoops trying to wrap their head around atomicity and questions like "wait, could that <code>value</code> have changed since I've done that <code>get</code>?". 

*AtomicMapOperations* lib provides the following constructs to abstract some of these difficulties away:         
  * <code>putOrTransform</code>
  * <code>transformIfPresent</code>
  * <code>putOrAggregate</code>
  * <code>aggregateIfPresent</code> 

In case of a map with values of type Long, typically used for counting hits for a given key, instead of the generic constructs mentioned earlier, the lib offers the dedicated methods: 
  * <code>increase</code> 
  * <code>decrease</code>

The AtmoicMapOpertaions lib has been successfuly used in <code>Storm</code> production environemnts with a <code>NonBlockingHashMap</code>(https://github.com/stephenc/high-scale-lib) being the underlying concurrent map implementation.

Aggregators vs. Transformers
=============================
While there may be a certain redundancy when it comes to these interfaces, since transformations can be expressed as aggregations and vice verse, each has its own benefits. Generally speaking, **transformation is best used when the next value is dependent only upon the previous value**, while **aggregations is best used when the next value is dependent both upon the previous value, and some input value**. The two exist side by side to allow fine tuning and reduce the number of object instances created to carry out operations on the underlying map. 
For instance, using a transformation to perform an aggregation may require creating a new transform (i.e., an instance of <code>Transformer</code>) per operation to hold a closure with the input value, as opposed to using an aggregator, which can be passed an input value from the outside and thus be reused.



Examples
=======

This will atomically increase the value of the key <code>now</code> in a non blocking manner:
 
    ConcurrentMap<Long, Long> hitsPerTimeStamp = new ConcurrentHashMap<Long, Long>();
    final long now = System.currentTimeMillis();
    
    NonBlockingOperations.forMap.withLongValues().increase(hitsPerTimeStamp, now);
    

This will set the key <code>myId</code> to the value of <code>System.currentTimeMillis()</code> regardless of whether it was present in the map before:

    ConcurrentMap<Long, Long> idToLastHitTimestamp = new ConcurrentHashMap<Long, Long>();
    final long myId = 12345L;
    
    final NonBlockingOperations.Transformer<Long> timestampTransformer = 
     new NonBlockingOperations.Transformer<Long>() {
           @Override
           public Long transform(final Long value) {
               // if our transformation depended on 'value', we'd have to check for nullity
               return System.currentTimeMillis();
           }
       };
    
    NonBlockingOperations.forMap.withImmutableValues().putOrTransform(idToLastHitTimestamp, 
                                                                      myId, 
                                                                      timestampTransformer);
    
This will aggregatively count even numbers only:

    ConcurrentMap<Long, Long> id2EvenNumbersCount = new ConcurrentHashMap<Long, Long>();
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

    NonBlockingOperations.forMap.withImmutableValues().putOrAggregate(id2EvenNumbersCount,
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

Or...    

    <dependency>
     <groupId>com.github.staslev</groupId>
     <artifactId>AtomicMapOperations</artifactId>
     <version>1.1-SNAPSHOT</version>
    </dependency>

(hosted by [sonatype](https://oss.sonatype.org/content/repositories/releases/) and [maven central](http://search.maven.org/))

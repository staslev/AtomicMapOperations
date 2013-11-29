AtomicMapOperations
===================
It is often non tirival to perform atomic actions on concurrent maps, especially when employing non-blocking paradigms. The *AtomicMapOperations* lib provides the following constructs to abstract some of these difficulties away: <code>putOrTransform</code>, <code>transformIfPresent</code>, <code>putOrAggregate</code>, <code>aggregateIfPresent</code>. 

In case of a map with values of type Long, typically used for counting hits for a given key, instead of the generic constructs mentioned earlier, the lib offers: <code>increase</code> and <code>decrease</code>.

While there may be a certian redundancy when it comes to expressiveness, since transformations can be expressed using aggregations and vice versa, each has its own benefits. Generally speaking, **transformation is best used when the next value is dependent only upon the previous value**, while **aggregaitions is best used when the next value is dependent both upon the previous value, and an input value**. Using each approprietly can reduce the number of object instances created to carry out an operation. For instance, using a transformation to perfrom an aggreation may require creating a new transform (i.e., an instance of the <code>Function</code> class) per opertaion, as opposed to using an aggregator, where a signle aggregator instance can be used for multiple operations.

Binaries
=========
    <dependency>
      <groupId>com.github.staslev</groupId>
      <artifactId>AtomicMapOperations</artifactId>
      <version>1.0</version>
    </dependency>

(hosted by [sonatype](https://oss.sonatype.org/content/repositories/releases/) and [maven central](http://search.maven.org/))

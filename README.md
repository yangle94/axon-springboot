#spring-boot项目基础搭建
## 此版本使用axon，进行事件驱动

1. 由于本项目为小型项目，仅仅实现了CQRS中的CQ，并未实现RS，RS请自行使用mybatis或者JPA进行查询

### 本用例中，使用的各种工具：

```
    CommandBus: SimpleCommandBus(默认，直接在发送线程里去执行command handler，执行后保存Aggregate状态和发送事件也都在同一个线程上，适用于大多数情况。)
    
    EventBus  : SimpleEventBus(默认的EventBus，不持久化event，一旦发送到消费者去，就会销毁)
    
    Repository : Standard Repositories 代表是GenericJpaRepository，直接把Aggregate的最新状态存到db去
    
    EventStorageEngine: JpaEventStorageEngine (默认,使用JPA进行存储)
    
    Serializer : XStream(默认)
```    

### 关于事务：

   由于在本例中使用的CommandBus为SimpleCommandBus，其中所有的执行操作都会在一个线程中进行，并不会有多线程的缘故，最简单的方法是在需要使用事务的地方直接在方法上加入Spring的@Transactional注解，即可保证事务
   
### 缺点：

   并发量大可能会导致线程不够，需要在servlet端限制线程数，避免线程过多引发的问题,请参阅jettyConfig.java
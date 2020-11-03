# spring-boot-starter-data-presto

presto-spring-boot-starter

- druid connection pool
- ssl enable
- code completion in IDE
- more configuration

## Use

1. add dependency

```xml
<dependency>
    <groupId>com.polarquant.data</groupId>
    <artifactId>presto-spring-boot-starter</artifactId>
    <version>1.0.0.RELEASE</version>
</dependency>
```

2. configuration

application.properties

```properties
spring.data.presto.jdbc.driver=com.facebook.presto.jdbc.PrestoDriver
spring.data.presto.jdbc.url= jdbc:presto://ark2:4285/hive/default
spring.data.presto.jdbc.username=isuhadoop
spring.data.presto.jdbc.password=abc
spring.data.presto.jdbc.ssl.enabled=true
spring.data.presto.jdbc.ssl.key-store-path=/usr/local/conf/presto.jks
spring.data.presto.jdbc.ssl.key-store-password=abc
spring.data.presto.pool.initial-size=1
spring.data.presto.pool.max-wait-millis=60000
```


3. use in spring boot

add `spring-boot-starter-jdbc` dependency to pom.xml

```java
@Autowired
@Qualifier("prestoJdbcTemplate")
JdbcTemplate prestoJdbcTemplate;

//invoke
List<Map<String, Object>> results = prestoJdbcTemplate.queryForList(sql);
```

## Example

see `./presto-started-example`
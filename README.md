# Flowee
Flowee is a framework for building Java services backed by one or more workflows. Its primary purpose is to provide groundwork for rule-driven workflow selection and execution. Developers can focus the majority of their efforts on building the tasks which hold the actual business requirements.

Workflows built on Flowee will run without the need for "containers" or "engines". The framework is lightweight and integrates seamlessly with any application.

Details are found in this three part blog series: [part 1](http://www.jramoyo.com/2013/05/introducing-flowee-framework-for.html), [part 2](http://www.jramoyo.com/2013/05/flowee-sample-application.html), part 3.

## Downloads
  * [1.0](https://drive.google.com/folderview?id=0BxIYRgMtzry_N3Z6UjlvbHhTeEE&usp=sharing 1.0)

## Maven
```xml
<dependency>
  <groupId>com.jramoyo.flowee</groupId>
  <artifactId>flowee-core</artifactId>
  <version>1.0</version>
</dependency>
```
Also include when integrating with Spring:
```xml
<dependency>
  <groupId>com.jramoyo.flowee</groupId>
  <artifactId>flowee-spring</artifactId>
  <version>1.0</version>
</dependency>
```

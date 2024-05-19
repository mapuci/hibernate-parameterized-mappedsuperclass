# hibernate-parameterized-mappedsuperclass
This projects demonstrates quarkus specific problems regarding lazy loading of parameterized entities from @Mapped superclass.

### modules
* **_quarkus3-hibernate-parameterized-mappedsuperclass_** 
  * here you can reproduce the problem. 
  * Simply run the test. (We all know how to do that)
  * I checked the generated bytecode (local build of hibernate lib, changed DEBUG = true in ByteBuddyState class.)
  * see [enhancedClasses](quarkus3-hibernate-parameterized-mappedsuperclass/enhancedClasses) dir. It looks fine to me - using decompiler.
* **_wildfly_hibernate_** here you can see the same functionality working normally.
  * The project is clone of wildfly quickstart for hibernate
  * https://github.com/wildfly/quickstart/tree/main/hibernate
  * I only added some code in the **_reproducer_** package, including entities, copied from quarkus app.
  * See [ReproducerRepo](wildfly-hibernate/src/main/java/org/jboss/as/quickstart/hibernate/reproducer/repo/ReproducerRepo.java)
  * It is invoked every time you refresh page [page](http://localhost:8080/wildfly-hibernate/index.jsf)
  * I ran and deployed WF with **_provisioned_server_** profile. With Intellij.
  * run **_mvn clean package -P provisioned_server_** inside **_wildfly-hibernate_** directory (this builds WAR and wildfly)
  * create new run config for local wildfly, it is installed in /target/server.
  * NOTE: I didn't export enhanced entity classes with wildfly. (yet) 

### I believe the problem occurs: (See TestResource.thisWillFail method)
* see decompiled classes [HERE](quarkus3-hibernate-parameterized-mappedsuperclass/enhancedClasses)
* call to getter 
```java
one.getTwo()
```
triggers code in `AbsOne`
```java
public TWO getTwo() {
    return this.$$_hibernate_read_two();
}
    
public AbsTwo $$_hibernate_read_two() {
  return this.two;
}
```

But the method $$_hibernate_read_two is `overridden` inside `One`
```java
public Two $$_hibernate_read_two() {
    if (this.$$_hibernate_getInterceptor() != null) {
        super.$$_hibernate_write_two((AbsTwo)this.$$_hibernate_getInterceptor().readObject(this, "two", super.$$_hibernate_read_two()));
    }

    return (Two)super.$$_hibernate_read_two();
}
```
**_But this overridden method is never called._**

### In example `TestResource.thisWillNotFailBecauseWeUseNonParameterizedAbstractGetterToInitLazyEntity`
you can see, that overriding of method`one.getAbsOneStringProp` works as expected... entity `two` and `three` are correctly lazy loaded.



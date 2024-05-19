That was generated with self built hibernate (latest dev branch, gradle task `publishToMavenLocal`). 
I only replaced the following line in `ByteBuddyState` class, 
so the classes were written to `tmp` dir and copied here.
```java
private static final boolean DEBUG = true;
```

I only did that for quarkus (dependency management), with wildfly I would need some more time, 
to figure how to build wildfly with specific hibernate version.

The decompiled classes look OK. The problem is probably the classloader.
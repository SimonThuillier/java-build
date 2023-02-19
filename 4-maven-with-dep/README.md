# handling project and outside world dependencies with maven

# 1 - the common dependency

If we package the app using the startPom.xml we get the following error : 
`[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.6.0:compile (default-compile) on project 4-maven-with-dep: Compilation failure: Compilation failure:
[ERROR] /home/simon/projects/kafka/java-build/4-maven-with-dep/src/main/java/com/bts/App.java:[3,18] package bts.common does not exist
[ERROR] /home/simon/projects/kafka/java-build/4-maven-with-dep/src/main/java/com/bts/App.java:[13,39] cannot find symbol
[ERROR]   symbol:   variable CommonHelper`

We try to compile without including the common package as a dependency. 
Indeed, maven checks completude of dependencies before compiling to prevent runtime errors. 

If the dependency is also a maven handled module we should use the dependency mechanism.
However, here common is not. So how can we do ? 

see https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html
The trick is to use the maven-install-plugin that will guess a maven module from the jar we want to use, then use it as a dependency. 

The easier way is with the above command : 
`mvn install:install-file -Dfile=../common.jar -DgroupId=com.bts -DartifactId=common -Dversion=0.1 -Dpackaging=jar`

Upon success the module common is installed in your local central maven repository : *~/.m2/repository/com/bts/common/0.1/common-0.1.jar*

After adding the dependency in our pom : 
`<dependency>
<groupId>com.bts</groupId>
<artifactId>common</artifactId>
<version>0.1</version>
</dependency>`

the clean and package command works !

However our dependency is not embedded in our jar. Hence the command `java -jar target/4-maven-with-dep-1.0-SNAPSHOT.jar` 
fails with `NoClassDefFoundError: com/bts/common/CommonHelper`.

To correct it we need to copy jar into the Class-Path of our maven application which is lib. 
copy the jar stored in *~/.m2/repository/com/bts/common/0.1/common-0.1.jar* into the lib folder of our module.
copy the target jar at the root of the module (because from it's manifest Class-Path is lib/)

Then command `java -jar 4-maven-with-dep-1.0-SNAPSHOT.jar` should work ! 


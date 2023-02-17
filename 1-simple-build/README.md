# here a simple hello world Main class is built with various build tools

# java files lifecycle
A **java** file contains Java code. 
A Java file is compiled by jdk to produce a **class** file that can be loaded by the JVM. 
A **Jar** file is a zip archive of other files, most likely class files.

# 1 - what intellij does
`/usr/lib/jvm/java-1.17.0-openjdk-amd64/bin/java \
-javaagent:/home/simon/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.7571.182/lib/idea_rt.jar=42573:/home/simon/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.7571.182/bin \
-Dfile.encoding=UTF-8 \
-classpath /home/simon/projects/kafka/java-build/out/production/simple-build Main`

this command structure is : 
`<jdk bin filepath> \
-javaagent:{<agent jarpath>[=<options>]} \
-Dfile.encoding=<file encoding> \
-classpath <classpath> <main class>`

- javagent : Provides services that allow Java programming language agents to instrument programs running on the JVM. The mechanism for instrumentation is modification of the byte-codes of methods, see **java.lang.instrument**.
- classpath : Classpath in Java is the path to the directory or list of the directory which is used by ClassLoaders to find and load classes in the Java program. Classpath can be specified using CLASSPATH environment variable which is case insensitive, -cp or -classpath command-line option or Class-Path attribute in manifest.mf file of the JAR file.

# 2 - package the out classes as a jar and run it
intellij stores the compiled classes in a subdirectory of the out folder.
this target directory can be modified in Project Settings > Project > Project compiler output field.
see https://www.jetbrains.com/help/idea/compiling-applications.html#compile_module

to embed all generated classes as a jar (which is merely a zip archive with classes and the manifest) uses : `jar cvf <program>.jar -C <path/to/classes> .`
Ex : `jar cvf simple-build.jar -C out/production/simple-build .`

then you can run the jar with : `java -jar simple-build.jar`
However this leads to the following error : **no main manifest attribute, in simple-build.jar**
This is because the jar doesn't know the entrypoint of the program.

Several things can be done to fix it. see https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html
The only one I was able to make work is : 
- at the root of the module simple-build create a META-INF directory , create in it a file MANIFEST.MF and add the following line : `Main-Class: Main`
- rebuild jar with command : `jar cvfm simple-build.jar simple-build/META-INF/MANIFEST.MF -C out/production/simple-build .`
- run it again. Now that the MANIFEST file is included  and specify the Main-Class it works. 



/usr/lib/jvm/java-1.17.0-openjdk-amd64/bin/java \
-Dfile.encoding=UTF-8 \
-classpath /home/simon/projects/kafka/java-build/out/production/simple-build Main




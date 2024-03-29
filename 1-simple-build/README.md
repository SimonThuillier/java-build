# first build method of a single module/directory using vanilla java tools

# java files lifecycle
A **java** file contains Java code. 
A Java file is compiled by jdk to produce a **class** file that can be loaded by the JVM. 
A **Jar** file is a zip archive of other files, most likely class files.

The process overview we'll see is then : 
    **.java files** --- *build* --> **.class files** --- *packaging* --> **.jar file** => *run*

# 1 - what intellij does
`/usr/lib/jvm/java-1.17.0-openjdk-amd64/bin/java \
-javaagent:/home/simon/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.7571.182/lib/idea_rt.jar=42573:/home/simon/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/223.7571.182/bin \
-Dfile.encoding=UTF-8 \
-classpath /home/simon/projects/kafka/java-build/out/production/1-simple-build Main`

this command structure is : 
`<jdk bin filepath> \
-javaagent:{<agent jarpath>[=<options>]} \
-Dfile.encoding=<file encoding> \
-classpath <classpath> <main class>`

- javagent : Provides services that allow Java programming language agents to instrument programs running on the JVM. The mechanism for instrumentation is modification of the byte-codes of methods, see **java.lang.instrument**.
- classpath : Classpath in Java is the path to the directory or list of the directory which is used by ClassLoaders to find and load classes in the Java program. Classpath can be specified using CLASSPATH environment variable which is case-insensitive, -cp or -classpath command-line option or Class-Path attribute in manifest.mf file of the JAR file.

the javaagent is important, that's it who command target directory for classes compilation.

# 1-B CLI javac alternative for compiling classes
You can get an equal result to intellij agent build using your jdk javac CLI tool. 
For big modules with packages/subdirectories it is better to get a list of all java files nested inside our module and use it as argument for javac.
see https://www.baeldung.com/javac-compile-classes-directory

In our example execute the following command :
`cd 1-simple-build/src && find . -type f -name "*.java" > javasources.txt && javac -d ../../out/production/1-simple-build @javasources.txt`

# 2 - package the out classes as a jar and run it
intellij stores the compiled classes in a subdirectory of the out folder.
this target directory can be modified in Project Settings > Project > Project compiler output field.
see https://www.jetbrains.com/help/idea/compiling-applications.html#compile_module

to embed all generated classes as a jar (which is merely a zip archive with classes and the manifest) uses : `jar cvf <program>.jar -C <path/to/classes> .`
Ex : `jar cvf 1-simple-build.jar -C out/production/1-simple-build .`

then you can run the jar with : `java -jar 1-simple-build.jar`
However this leads to the following error : **no main manifest attribute, in 1-simple-build.jar**
This is because the jar doesn't know the entrypoint of the program.

Several things can be done to fix it. see https://docs.oracle.com/javase/tutorial/deployment/jar/appman.html
The only one I was able to make work is : 
- at the root of the module 1-simple-build create a META-INF directory , create in it a file MANIFEST.MF and add the following line : `Main-Class: Main`
- rebuild jar with command : `jar cvfm 1-simple-build.jar 1-simple-build/META-INF/MANIFEST.MF -C out/production/1-simple-build .`
- run it again. Now that the MANIFEST file is included  and specify the Main-Class it works. 

tip : use `jar tf 1-simple-build.jar` to list the content of our jar file.

# whole build-package-run CLI command
to execute since project root.

`cd 1-simple-build/src && find . -type f -name "*.java" > javasources.txt && javac -d ../../out/production/1-simple-build @javasources.txt \
&& cd ../.. && jar cvfm 1-simple-build.jar 1-simple-build/META-INF/MANIFEST.MF -C out/production/1-simple-build . && java -jar 1-simple-build.jar`





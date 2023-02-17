# build of two modules using vanilla java tools

# 1 - what intellij does

Always using the intellij agent all classes throughout all necessary modules are well compiled

More about custom java agents : https://www.baeldung.com/java-instrumentation

# 2 - package the out classes as a jar and run it

Let's try to package the out classes as a jar and run it. We will include the manifest file right now so that java knows which class is the entrypoint of the program.
`jar cvfm 2-modular-build.jar 2-modular-build/META-INF/MANIFEST.MF -C out/production/2-modular-build .`
`java -jar 2-modular-build.jar`

We get the following error : 
`Exception in thread "main" java.lang.NoClassDefFoundError: bts/common/CommonHelper
at Main.main(Main.java:5)`
`

it's because we included only out/production/2-modular-build classes in our jar. 
We need to include the out/production/common classes too. However, we should treat it as a dependency. 

## The not embedded way with classpath

First then we have to package a jar for the common module. After compiling it to classes with intellij, we can do that with the following command :
`jar cvf common.jar -C out/production/common .`
After running it we should see common.jar has been created.
Now we have two ways to add it as a dependency to our 2-modular-build.jar.

in our manifest file we can add the following line : `Class-Path: common.jar` (it can be several filepaths space separated)

then repackage the jar and running it will work. However, we must never change relative path of common.jar respective to 2-modular-build.jar.
This would cause the runtime ClassLoader not to find it and produce again the NoClassDefFoundError we got before.

For this reason it can be better to embed the dependency in the jar with the following method.

## The embedded way

remove the `Class-Path` line from the manifest file and repackage it. the Class-Path default value being . which is the directory specified by -C option (out/production/common) in the above.

After packaging let's use `jar tf 2-modular-build.jar` to see the content of the jar. We get :
`META-INF/
META-INF/MANIFEST.MF
bts/
bts/modularbuild/
bts/modularbuild/Main.class
`

Now update it with the command : 
`jar uf 2-modular-build.jar -C out/production/common .`
We can check the content with `jar tf 2-modular-build.jar` to ensure update was successful. We should now get :
`META-INF/
META-INF/MANIFEST.MF
bts/
bts/modularbuild/
bts/modularbuild/Main.class
bts/common/
bts/common/CommonHelper.class`

Creating and updating the jar was a good way to learn but it much more efficient to embed dependencies at jar creation with this command :
`jar cvfm 2-modular-build.jar 2-modular-build/META-INF/MANIFEST.MF -C out/production/2-modular-build . -C out/production/common .`

*According to use cases you may want to use either the not embedded way, particularly if a dependency module is widely used, or the embedded way !*

## more about manifest files
https://docs.oracle.com/javase/tutorial/deployment/jar/index.html
https://www.baeldung.com/java-jar-manifest

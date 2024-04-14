### Info

This directory contains CommandLineParser class. The original development location is `commandline-parser` directory in __selenium_java__ [repository](https://github.com/sergueik/selenium_java/tree/master/commandline-parser)

### Usage

To see how the code operates, consider running the provided unit tests first:
```sh
mvn test
```
this will exercise code in `src/test/java`:
```sh
Tests run: 26, Failures: 0, Errors: 0, Skipped: 2
```
that cover the class methods extensively and updated more frequently than this __README.md__.

#### Internals
#### Standard Argument
* Flags with value
```java
CommandLineParser c = new CommandLineParser();

c.flagsWithValues.add("option");
boolean debug = true;
c.setDebug(debug);
c.parse(args);

c.getFlagValue("option");
```
alternatively, in early revisions one could call
```java
option = c.flags.get("option");
```
* Switches
to introduce the boolean variables (switches) the syyntax is even simpler: no need to `add` to `flagsWithValues`:

```java
CommandLineParser c = new CommandLineParser();
c.parse(args);
boolean switch = c.hasFlag("switch");
```

alternatively, in early revisions one could call
```java
if (c.flags.containsKey("switch")) {
  boolean switch = true;
}
```
####  Extended Arguments
##### Environment values

* the following sytnac command line option indicates the environent variable reference:
```sh
export MY_ENVIRONMENT=value
java Application -flag env:MY_ENVIRONMENT
```
where inside the `Appilcation` class the value of the `flag`
will be
```java
System.getenv("MY_ENVIRONMENT")
```

##### File with lists of data (or JSON or YAML)
* Command line parser understands the file path reference through the following syntax:
```sh
java Application -option @filepath
```
this will load the non-blank lines of `filepath` contents and join via `,` to become the value of `option`. 

NOTE: there is a  work in progress to  provide the path to a file or URI using the standard syntax:
```sh
java ApplicationClass -data http://echc.jsontest.com/key/value/one/two
java ApplicationClass -data file:///<filepath>
```
The notation may change in the future release

#### JSON, YAML
The JSON format is recognized too, and is read using either [org.json.JSONObject](https://stleary.github.io/JSON-java/org/json/JSONObject.html) or [com.google.gson.Gson](https://javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/module-summary.html). 
Which one, is governed by invoking method:
```java
c.setValueFormat("GSON")
```
The file or URI contents are still  returned as a string data, but internally it is validated  to be serializalbe valid json object.
the Kubernetes-stye YAML support is a work in progres, see the details in the code.
### TODO
on Linux failing hard
```text
[INFO] Running example.CommonsCommandLineParserTest
null
[INFO] 
[INFO] Results:
[INFO] 
[WARNING] Tests run: 33, Failures: 0, Errors: 0, Skipped: 4
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 8.503 s
[INFO] Finished at: 2024-04-13T20:04:55-04:00
[INFO] Final Memory: 15M/60M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.22.2:test (default-test) on project commandline-parser: There are test failures.
[ERROR] 
[ERROR] Please refer to /home/sergueik/src/commandline-parser/target/surefire-reports for the individual test results.
[ERROR] Please refer to dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] The forked VM terminated without properly saying goodbye. VM crash or System.exit called?
[ERROR] Command was /bin/sh -c cd /home/sergueik/src/commandline-parser && /usr/lib/jvm/java-11-openjdk-amd64/bin/java -jar /home/sergueik/src/commandline-parser/target/surefire/surefirebooter6619296238443313076.jar /home/sergueik/src/commandline-parser/target/surefire 2024-04-13T20-04-52_342-jvmRun1 surefire4143110887948661821tmp surefire_015204690934999595245tmp
[ERROR] Process Exit Code: 0
[ERROR] Crashed tests:
[ERROR] example.CommonsCommandLineParserTest
[ERROR] org.apache.maven.surefire.booter.SurefireBooterForkException: The forked VM terminated without properly saying goodbye. VM crash or System.exit called?
[ERROR] Command was /bin/sh -c cd /home/sergueik/src/commandline-parser && /usr/lib/jvm/java-11-openjdk-amd64/bin/java -jar /home/sergueik/src/commandline-parser/target/surefire/surefirebooter6619296238443313076.jar /home/sergueik/src/commandline-parser/target/surefire 2024-04-13T20-04-52_342-jvmRun1 surefire4143110887948661821tmp surefire_015204690934999595245tmp
[ERROR] Process Exit Code: 0
[ERROR] Crashed tests:
[ERROR] example.CommonsCommandLineParserTest
[ERROR] at org.apache.maven.plugin.surefire.booterclient.ForkStarter.fork(ForkStarter.java:669)
[ERROR] at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:282)
[ERROR] at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:245)
[ERROR] at org.apache.maven.plugin.surefire.AbstractSurefireMojo.executeProvider(AbstractSurefireMojo.java:1183)
[ERROR] at org.apache.maven.plugin.surefire.AbstractSurefireMojo.executeAfterPreconditionsChecked(AbstractSurefireMojo.java:1011)
[ERROR] at org.apache.maven.plugin.surefire.AbstractSurefireMojo.execute(AbstractSurefireMojo.java:857)
[ERROR] at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:134)
[ERROR] at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:207)
[ERROR] at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:153)
[ERROR] at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:145)
[ERROR] at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:116)
[ERROR] at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:80)
[ERROR] at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build(SingleThreadedBuilder.java:51)
[ERROR] at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:128)
[ERROR] at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:307)
[ERROR] at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:193)
[ERROR] at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:106)
[ERROR] at org.apache.maven.cli.MavenCli.execute(MavenCli.java:863)
[ERROR] at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:288)
[ERROR] at org.apache.maven.cli.MavenCli.main(MavenCli.java:199)
[ERROR] at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
[ERROR] at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
[ERROR] at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
[ERROR] at java.base/java.lang.reflect.Method.invoke(Method.java:566)
[ERROR] at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:289)
[ERROR] at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:229)
[ERROR] at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:415)
[ERROR] at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:356)
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException

```

### See Also
 * [introduction to Creational Design Patterns](https://www.baeldung.com/creational-design-patterns)
  * Apache CLI CommandLineParser [tutorial](https://commons.apache.org/proper/commons-cli/usage.html)
  
### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

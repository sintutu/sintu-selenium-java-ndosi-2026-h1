# Maven Stuff

## TABLE 1 — Maven Lifecycle Phases / Goals

* Column 1 = Phase you invoke
* Column 2 = What it actually does
* Column 3 = What it depends on (previous phases)
* Column 4 = Which of your configured plugins act here

| Phase         | What It Means                                                              | Depends On                         | Your Plugins Acting Here                               |
|:--------------|:---------------------------------------------------------------------------|:-----------------------------------|:-------------------------------------------------------|
| clean         | Deletes target directory                                                   | none                               | none (default clean plugin)                            |
| validate      | Validate project structure and run checks bound to validate                | clean (if run before)              | maven-checkstyle-plugin (check goal bound to validate) |
| compile       | Compile src/main/java to target/classes                                    | validate                           | maven-compiler-plugin (default, implicit)              |
| test-compile  | Compile src/test/java to target/test-classes                               | compile                            | maven-compiler-plugin (default, implicit)              |
| test          | Execute tests                                                              | test-compile                       | maven-surefire-plugin (test goal)                      |
| package       | Create JAR from compiled main classes                                      | test                               | none of yours explicitly                               |
| install       | Install artifact into local Maven repository                               | package                            | none of yours explicitly                               |
| surefire:test | Run surefire plugin test goal directly (bypasses lifecycle chain above it) | requires compiled classes to exist | maven-surefire-plugin                                  |

Important clarification:  
When you run `mvn test`, Maven automatically runs:  
`validate → compile → test-compile → test`   
That is not Maven "changing nature". It is deterministic phase *chaining*.

Pipeline engine = build engine. Same thing. It orchestrates ordered phases.

## TABLE 2 — Your Plugins

* Column 1 = Plugin
* Column 2 = What It Does
* Column 3 = Which Phase / Goal It Acts On
* Column 4 = What Happens When You Run mvn test

| Plugin                  | What It Does                                    | Bound Phase / Goal                           | Effect During mvn test                                                          |
|:------------------------|:------------------------------------------------|:---------------------------------------------|:--------------------------------------------------------------------------------| 
| maven-surefire-plugin   | Forks JVM and runs tests via detected framework | test phase (goal: test)                      | Runs after test-compile; forks JVM; executes TestNG                             |
| maven-checkstyle-plugin | Runs static code analysis using rules           | validate phase (goal: check)                 | Runs before compilation every time mvn test is executed                         |
| spotless-maven-plugin   | Formats or checks code formatting               | Not bound to lifecycle (no executions block) | Does nothing unless you explicitly run mvn spotless:apply or mvn spotless:check |

Important:  
Spotless currently has no `<executions>` binding.  
So `mvn test` does NOT run Spotless.  

Checkstyle does run because you bound it to `validate`.

## TABLE 3 — Commands You Care About

* Column 1 = Windows-safe command
* Column 2 = What It Does (referencing tables above)

| Command                                  | What You Are Doing                                                                                   |
|:-----------------------------------------|:-----------------------------------------------------------------------------------------------------|
| mvn clean                                | Deletes target directory only                                                                        |
| mvn validate                             | Runs validate phase; triggers checkstyle; no compilation yet                                         |
| mvn compile                              | Runs validate + compile; compiles main code only                                                     |
| mvn test-compile                         | Runs validate + compile + test-compile; compiles test code but does not execute tests                |
| mvn test                                 | Runs validate + compile + test-compile + test; triggers checkstyle and surefire                      |
| mvn surefire:test                        | Runs only the surefire plugin test goal; assumes code already compiled; skips validate/compile chain |
| mvn surefire:test -Dcheckstyle.skip=true | Runs surefire only; explicitly prevents checkstyle if validate accidentally triggered                |
| mvn spotless:apply                       | Runs spotless plugin goal apply; formats code; does not run lifecycle                                |
| mvn checkstyle:check                     | Runs checkstyle plugin directly; independent of lifecycle binding                                    |
| mvn test -Dcheckstyle.skip=true          | Runs full lifecycle but skips checkstyle during validate                                             |

For Windows, quoting pattern example (when needed):
`mvn surefire:test "-Dtest=LoginTests"` 

## Performance Benchmarking Relevance

If your goal is to benchmark only test execution (fork + TestNG + harness):

Use:
`mvn surefire:test`

If your goal is to benchmark full CI-like pipeline:

Use:
`mvn clean test`

If your goal is to benchmark only browser + harness:

Use:
`mvn test-compile exec:java "-Dexec.mainClass=manual.ManualTestRunner" "-Dexec.classpathScope=test"`

> mvn test-compile shouldn't run tests. Yet I see the test result from ManualTestRunner. Am I missing a plugin like exec?

mvn test-compile
should NOT run tests.

It only runs:

validate → compile → test-compile

That compiles code. Nothing executes.

So if you see ManualTestRunner output when running only:

mvn test-compile

Then something else is happening.

Now let’s reason precisely.

There are only three possibilities.

You actually ran:
mvn test-compile exec:java ...
(in which case exec plugin is running ManualTestRunner)

You ran:
mvn test
which triggers surefire.

You have a plugin bound to test-compile phase that executes something.

Given your pom.xml, you do NOT have exec plugin bound to any lifecycle phase.

You only ran exec explicitly earlier using:

mvn test-compile exec:java "-Dexec.mainClass=manual.ManualTestRunner" "-Dexec.classpathScope=test"

That means:

test-compile ran
then exec:java goal ran

Those are two separate goals on the same command line.

Maven allows chaining goals like that.

So:

mvn test-compile exec:java

means:

Run lifecycle up to test-compile

Then run exec plugin’s java goal

It does NOT mean test-compile executed ManualTestRunner.

The exec plugin did.

So yes — you ARE using exec plugin.

You just didn’t declare it in your pom.xml.

Because Maven can resolve plugins dynamically from the command line.

When you type:

mvn exec:java

Maven downloads and executes the exec-maven-plugin even if it is not declared in your build section.

That’s why you saw downloads earlier.

So to answer your question directly:

No, test-compile does not run tests.

Yes, you are invoking exec plugin implicitly when you add exec:java to the command.

You are not missing anything.

You are correctly invoking an additional plugin goal.

Here is the mental model:

mvn test-compile exec:java

means:

Phase: test-compile
Then: plugin goal exec:java

They are independent steps.

If you run only:

mvn test-compile

You should NOT see:

Total execution time: ...

If you do, then something is bound incorrectly.

But based on your pom, it is not.

Now I want to confirm something.

When you say:

"I see the test result from ManualTestRunner"

Was that after running only:

mvn test-compile

Or after running:

mvn test-compile exec:java ...

Be precise.

Because if it was truly just test-compile, then we have something deeper to inspect.

If it was test-compile exec:java, then everything is behaving correctly.
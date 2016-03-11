mock-http-server
================================================
A tiny test tool for Scala projects to test HTTP calls

---

This repository contains both a Scala trait and its concrete class implementation useful to start an embedded Jetty server wrappable over functions.

It can be used for unit testing of HTTP callers.

For example:

```scala
class TestClassSpec extends FlatSpec with Matchers with MockHttpServerLike {


   it should "invoke the remote server" in {

    val response = "This is the server response"

    withinMockServer(5484, None, response) {
      val mockServerResponse = FUNCTION_TO_TEST
      mockServerResponse shouldBe response
    }

  }
```

The trait `MockHttpServerLike` defines the `withinMockServer` method that given:

 - `Integer` port
 - `Option[Long]` timeout
 - `String` response
 - function to execute

will start a Mock Server that remains active until the function to execute will return.

### How to use it

Add the following dependency to your Scala projects

```scala
libraryDependencies += "com.github.brokersquare" % "mock-http-server" % "0.0.1"
```

### How to help

So far, it is a really vanilla implementation. If you have any valuable idea to improve it please fork and PR as usual.

Enjoy! The Brokersquare team

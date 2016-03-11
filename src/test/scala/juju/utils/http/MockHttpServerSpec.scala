package juju.utils.http

import org.scalatest.FlatSpec
import org.scalatest.Matchers



class MockHttpServerSpec extends FlatSpec with Matchers with MockHttpServerLike {
  
  it should "properly contains variables" in {
    val p = new MockHttpServer(8080, None, "Hello")
    
    p.port shouldBe 8080
    p.timeout shouldBe None
    p.response shouldBe "Hello"
    
  }
  
   it should "properly execute functions within the body of mock server" in {
    val p = new MockHttpServer(8080, None, "Hello")
    
    p.run {
      assert(true, "Function running within the server")
    }
    
    
    p.run {
      val content = getContent("http://localhost:8080")
      content shouldBe "Hello"
    }
    
  }
   
   it should "properly mixin the server trait" in {
   
    val response = "This is the server response" 
     
    withinMockServer(5484, None, response) {
      val content = getContent("http://localhost:5484")
      content shouldBe response
    }
    
  }
   
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  private def getContent(queryPath: String,
             connectionTimeout: Int = 5000,
             readTimeout: Int = 500,
             requestMethod: String = "GET"
             ): String = {
    import java.net.{URL, HttpURLConnection}
    val connection = new URL(queryPath).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectionTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if(inputStream != null) inputStream.close
    content
  }
   
   
  
}


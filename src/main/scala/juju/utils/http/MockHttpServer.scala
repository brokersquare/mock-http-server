package juju.utils.http

import java.io.IOException

import scala.util.Try

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Trait to build Mock HTTP Servers.
 * 
 * Usable for Scala tests
 * 
 */
trait MockHttpServerLike {

	/**
	 * This function can be used as wrapper on unit tests to create a mock http server on the fly.
	 * This server, if properly created will respond locally to the HTTP port passed as input.
	 * When the <code>f</code> function completes its execution, the mock server itself is stopped.
	 * 
	 * @param port: Integer - the HTTP port opened for this server
	 * @param timeout: Option[Long] - optional, a sleeping timeout for the server. None by default.
	 * @param response: String - optional, a string returned as server response. "Ack!" by default.
	 * 
	 */
	def withinMockServer(port: Integer, timeout: Option[Long] = None, response: String = "Ack!")(funct: => Unit) {
	  
		var server: Option[Server] = None
		Try {
		  server = Some(new Server(port));
			server.get.setHandler(new RootHandler(response, timeout));
			server.get.start() 
			funct
			server
		} getOrElse(server) match {
		    case Some(s) => s.stop()
		    case None => 
		}
	}
}

/**
 * Concrete implementation of server
 */
class MockHttpServer(val port: Integer = 8080, val timeout: Option[Long] = None, val response: String = "Ack!") extends MockHttpServerLike {

 
	/**
	 * It runs the function passed as input within a Mock Server context
	 */
	def run(f: => Unit) { 
		withinMockServer(port, timeout, response)(f) 
	}

}

/**
 * This implements a basic handler to process incoming server requests and producing mock server response.
 * 
 */
class RootHandler(handledResponse: String, timeout: Option[Long]) extends AbstractHandler {

	@throws(classOf[java.io.IOException])
	@throws(classOf[javax.servlet.ServletException])
	override def handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse)  {

		timeout match {
		case Some(sleepTime) => Thread.sleep(sleepTime)
		case None => 
		}

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(handledResponse);
		baseRequest.setHandled(true);
	}

}

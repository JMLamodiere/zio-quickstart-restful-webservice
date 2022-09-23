package dev.zio.quickstart.greet

import zhttp.http.*

/**
 * An http app that: 
 *   - Accepts a `Request` and returns a `Response`
 *   - Does not fail
 *   - Does not use the environment
 */
object GreetingApp:
  def apply(): Http[Any, Nothing, Request, Response] =
    Http.collect[Request] {
      // GET /greet?name=:name
      case req @ (Method.GET -> !! / "greet") if (req.url.queryParams.nonEmpty) =>
        Response.text(s"Hello ${req.url.queryParams("name").mkString(" and ")}!")

      // GET /greet
      case Method.GET -> !! / "greet" =>
        Response.text(s"Hello World!")

      // GET /greet/:name
      case Method.GET -> !! / "greet" / name =>
        Response.text(s"Hello $name!")

      // GET /stats
      case req @ Method.GET -> !! / "useragent" =>
        Response.text(s"User-Agent: ${req.headerValue("User-Agent").getOrElse("Unknown")}")
    }

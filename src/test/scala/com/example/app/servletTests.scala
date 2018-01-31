package com.example.app

import org.scalatra.test.scalatest._

class servletTests extends ScalatraFunSuite {

  addServlet(classOf[servlet], "/*")

  test("GET / on servlet should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}

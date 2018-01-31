package com.example.app
// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._
import org.scalatra._

class servlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  
  before() {
    contentType = formats("json")
  }

  get("/messages") {
    messages
  }
  
  //returns only one message that has the same id as :id parameter  
  get("/messages/:id"){
    val desiredId = findMessage(params("id").toInt)
    if (desiredId != -1){
      messages(desiredId).text
    }else{
      "No message with requested id exists"
    }

  }

  //update message with id the same as :id parameter
  put("/messages/:id"){
    val curId : Int = params("id").toInt
    val desiredId = findMessage(curId)
    val newText = (parsedBody \ "text").extract[String]
    val newMsg = Message(curId,newText)
    messages = messages.updated(desiredId, newMsg)
    messages
  }

  def findMessage(fid : Int) : Int = messages.indexWhere(_.id == fid)
  
  //delete a message with id the same as :id parameter
  delete("/messages/:id"){
    messages = messages.filter(_.id!=params("id").toInt)
    messages
  }

  post("/messages"){
    messages = parsedBody.extract[Message] :: messages
    messages
  }
  var messages = List(
    Message(1,"Hi its test"),
    Message(2,"LOLOLO"),
    Message(3,"LALALAL")) 
}
case class Message(id:Int,text:String)


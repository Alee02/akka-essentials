package com.dp.actors

import akka.actor.{Actor, ActorSystem, Props}

object ActorCapabilities extends App {

   class SimpleActor extends Actor {
    override def receive: Receive = {
      case message: String => println(s"[simple actor] I have received $message")
      case number: Int => println(s"[simple actor] I have received $number")
      case SpecialMessage(contents) => println(s"[simple actor] I have received special message: $contents")
    }
  }

  val system = ActorSystem("actorCapabilitiesDemo")

  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  simpleActor ! "hello, actor"

  // 1 - messages can be of any type
  simpleActor ! 42

  case class SpecialMessage(contents: String)
  simpleActor ! SpecialMessage("special content")

  // 2 - actors have information about their context and about themselves
}

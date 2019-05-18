package com.dp.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorsIntro extends App {
  // Actor systems

  // Heavyweight data structure that controls a number of threads under the hood that allocates to running actors
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  // create actors (Word count actor
  /**
    * Actors are uniquely identified
    * Messages are asynchronous
    * Each actor may respond differntly
    */

  class WordCountActor extends Actor {
    var totalWords = 0

    def receive: PartialFunction[Any, Unit] = {
      case message: String => {
        println(s"[word counter] i have received: $message")
        totalWords += message.split(" ").length
      }
      case msg => println(s"s[word counter] I cannot understand ${msg.toString}")
    }
  }

  // instantiate our actor
  val wordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "wordCounter")
  val anotherWordCounter: ActorRef = actorSystem.actorOf(Props[WordCountActor], "anotherWordCounter")

  wordCounter ! "I am learning Akka nad it's pretty damn cool!"
  anotherWordCounter ! "I different message"

  /*
  We cannot get the actor object but only the actor reference so we cannot direclty communicate with the object
  Therefore actors are fully encapsulated
   */
  object Person {
    def props(name: String) = Props(new Person(name))
  }

  class Person(name: String) extends Actor {
    override def receive: Receive = {
      case a if a == "hi" => {
        println(s"[person] you said: $a")
        println(s"Hi, my name is $name")
      }
      case _ => println("I don't know what you want")
    }
  }

//  val personActor = actorSystem.actorOf(Props(new Person("Bob")))
  val personActor = actorSystem.actorOf(Person.props("Bob")) // Best practice with a props method
  personActor ! "hi"

}

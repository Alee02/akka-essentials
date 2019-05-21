package com.dp.exercises

import akka.actor.{Actor, ActorSystem, Props}

object CounterActorExample extends App {


  class CounterActor extends Actor {
    var counter: Int = 0

    override def receive: Receive = {
      case Increment => counter += 1
      case Decrement => counter -= 1
      case Print => println(counter)
    }
  }
  object Increment
  object Decrement
  object Print

  /*
  TESTING
   */
  val system = ActorSystem("counterActorSystem")
  val counterActor = system.actorOf(Props[CounterActor], "MrCount")

  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Decrement
  counterActor ! Print

}
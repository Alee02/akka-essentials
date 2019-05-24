package com.dp.exercises

import akka.actor.{Actor, ActorSystem, Props}
import com.dp.exercises.CounterActorExample.CounterActor
import com.dp.exercises.StatelessCounterActorExample.StatelessCounter.{Decrement, Increment, Print}

object StatelessCounterActorExample extends App {

  class StatelessCounter extends Actor {
    import StatelessCounter._

    override def receive: Receive = counterRecieve(0)

    def counterRecieve(currentCount: Int): Receive = {
      case Increment => context.become(counterRecieve(currentCount + 1))
      case Decrement => context.become(counterRecieve(currentCount - 1))
      case Print => println(s"[counter] my counter count is $currentCount")
    }
//    {
//      case Increment =>
//      case Decrement =>
//      case Print =>
//    }
  }

  object StatelessCounter {
    object Increment
    object Decrement
    object Print
  }


  /*
 TESTING
  */
  val system = ActorSystem("statleessCounterActorSystem")
  val counterActor = system.actorOf(Props[StatelessCounter])

  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Decrement
  counterActor ! Print

}

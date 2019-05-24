package com.dp.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.dp.actors.ChangeActorBehaviour.Mom.MomStart

object ChangeActorBehaviour extends App {

  object FussyKid {
    case object Accept
    case object Reject
    val HAPPY = "happy"
    val SAD = "sad"
  }

  class FussyKid extends Actor {
    import FussyKid._
    import Mom._

    var state = HAPPY
    override def receive: Receive = {
      case Food(VEGETABLE) => state = SAD
      case Food(CHOCOLATE) => state = HAPPY
      case Ask(_) =>
        if (state == HAPPY) sender() ! Accept
        else sender() ! Reject
    }
  }

  object Mom {
    case class MomStart(kidRef: ActorRef)
    case class Food(food: String)
    case class Ask(message: String)
    val VEGETABLE = "veggies"
    val CHOCOLATE = "chocolate"
  }

  class Mom extends Actor {
    import Mom._
    import FussyKid._

    override def receive: Receive = {
      case MomStart(kid) =>
        // test out interaction
        kid ! Food(VEGETABLE)
        kid ! Ask("do you want to play?")
      case Accept => println("Yay, my kid is happy!")
      case Reject => println("My kid is sad, but at least he's healthy")
    }
  }

  val system = ActorSystem("changingActorBehaviourDEMO")

  val mom = system.actorOf(Props[Mom])
  val fussyKid = system.actorOf(Props[FussyKid])

  mom ! MomStart(fussyKid)


}

package com.dp.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCapabilities extends App {

   class SimpleActor extends Actor {
     // message handler for a the actor
     // a PartialFunction[Any, Unit]
    override def receive: Receive = {
      case "Hi" => context.sender() ! "Hello There!"
      case message: String => {
        println(s"${self.path.name} sends message")
        println(s"to ${context.sender().path.name}")
        println(s"[message: simple actor] I have received $message")
      }
      case number: Int => println(s"[number : simple actor] I have received $number")
      case SpecialMessage(contents) => println(s"[SpecialMessage: simple actor] I have received special message: $contents")
      case SendMessageToYourself(contents) => {
        self ! contents
      }
      case SayHiTo(ref) => ref ! "Hi"
      case WirelessPhoneMessage(content, ref) => ref forward (content + "s") // i keep the original sender of the WPM
    }
  }

  /*
   Actors need infrastructure
    */
  // Actors are created via the actor system and not via the traditional new Actor keyword
  val system = ActorSystem("actorCapabilitiesDemo")

  /*
   Actor principles
    */
  // Actors are fully encapsulated, only their actorRef is used
  // full parallelism
  // non-blocking interaction via messages
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  // ! is the "tell" method
  simpleActor ! "hello, actor"

  // 1 - messages can be of any type
//  simpleActor ! 42

  case class SpecialMessage(contents: String)
//  simpleActor ! SpecialMessage("special content")

  // 2 - actors have information about their context and about themselves
  case class SendMessageToYourself(content: String)
//  simpleActor ! SendMessageToYourself("some special content")

  // 4 - actors can REPLY to messages
  val alice = system.actorOf(Props[SimpleActor], "alice")
  val bob = system.actorOf(Props[SimpleActor], "bob")

  case class SayHiTo(ref: ActorRef)

  alice ! SayHiTo(bob)

  // context.sender()
  // at any moment the sender is the last actor to send a message to the current actor

  alice ! "Hi"
  // This message will be sent to deadLetters since there is no actor specified
  // deadLetters is the garbage pool of messages that are not sent to any actor.

  /*
   Actor references
    */
  // can be sent
  // the self reference

  // 5 - forwarding messages
  // D -> A -> B
  // forwarding = sending a message with the ORIGINAL sender

  case class WirelessPhoneMessage(content: String, ref: ActorRef)
  alice ! WirelessPhoneMessage("Hi", bob)
}

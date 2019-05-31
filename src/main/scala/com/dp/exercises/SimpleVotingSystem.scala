package com.dp.exercises

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object SimpleVotingSystem extends App {

  case class Vote(candidate: Candidate)
  case object VoteStatusRequest
  case class VoteStatusReply(candidate: Option[String])

  trait Person

  class Citizen extends Person with Actor {
    override def receive: Receive = ???
  }

  case class AggregateVotes(citizen: Set[ActorRef])


  class VoteAggregator extends Actor {
    override def receive: Receive = ???
  }


  case class Candidate(name: String) extends Person


  val system = ActorSystem("votingActorSystem")

  val alice = system.actorOf(Props[Citizen])
  val bob = system.actorOf(Props[Citizen])
  val charlie = system.actorOf(Props[Citizen])
  val daniel = system.actorOf(Props[Citizen])


}

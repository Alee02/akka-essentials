package com.dp.exercises

import akka.actor.{Actor, ActorRef}

object SimpleVotingSystem extends App {

  case class Vote(candidate: Candidate)

  trait Person

  class Citizen extends Person with Actor {
    override def receive: Receive = ???
  }

  class AggregateVotes(citizen: Set[ActorRef]) extends Actor {
    override def receive: Receive = ???
  }

  case class Candidate(name: String) extends Person

}

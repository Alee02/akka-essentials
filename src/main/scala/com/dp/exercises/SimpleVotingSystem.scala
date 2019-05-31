package com.dp.exercises

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object SimpleVotingSystem extends App {

  case class Vote(candidate: Candidate)
  case object VoteStatusRequest
  case class VoteStatusReply(candidate: Option[Candidate])

  trait Person

  class Citizen extends Person with Actor {
    var candidate: Option[Candidate] = None
    override def receive: Receive = {
      case Vote(c) => candidate = Some(c)
      case VoteStatusRequest => sender() ! VoteStatusReply(candidate)
    }
  }

  case class AggregateVotes(citizen: Set[ActorRef])

  class VoteAggregator extends Actor {
    var stillWaiting: Set[ActorRef] = Set()
    var currentStats: Map[String, Int] = Map()
    override def receive: Receive = {
      case AggregateVotes(citizens) =>
        stillWaiting = citizens
        citizens.foreach(cRef => cRef ! VoteStatusRequest)
    }
  }

  case class Candidate(name: String) extends Person


  val system = ActorSystem("votingActorSystem")

  val alice = system.actorOf(Props[Citizen])
  val bob = system.actorOf(Props[Citizen])
  val charlie = system.actorOf(Props[Citizen])
  val daniel = system.actorOf(Props[Citizen])

  alice ! Vote(Candidate("Martin"))
  bob ! Vote(Candidate("Joanas"))
  charlie ! Vote(Candidate("Roland"))
  daniel ! Vote(Candidate("Roland"))

  val voteAggregator = system.actorOf(Props[VoteAggregator])

  voteAggregator ! AggregateVotes(Set(alice, bob, charlie, daniel))

  /*
  Print the status of the votes.
   */


}

package com.dp.exercises

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object SimpleVotingSystem extends App {

  case class Candidate(name: String) extends Person
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
    var currentStats: Map[Candidate, Int] = Map()
    override def receive: Receive = {
      case AggregateVotes(citizens) =>
        stillWaiting = citizens
        citizens.foreach(cRef => cRef ! VoteStatusRequest)
      case VoteStatusReply(None) =>
        // a citizen hasn't voted yet
        sender() ! VoteStatusRequest
      case VoteStatusReply(Some(candidate)) =>
        val newStillWaiting = stillWaiting - sender()
        val currentVotesOfCandidate = currentStats.getOrElse(candidate, 0)
        currentStats = currentStats + (candidate -> (currentVotesOfCandidate + 1))
        if (newStillWaiting.isEmpty) {
          println("[aggregator] poll stats:")
            currentStats.foreach(println)
        } else {
          stillWaiting = newStillWaiting
        }
        // a citizen has voted
    }
  }

  val system = ActorSystem("votingActorSystem")

  val alice = system.actorOf(Props[Citizen])
  val bob = system.actorOf(Props[Citizen])
  val charlie = system.actorOf(Props[Citizen])
  val daniel = system.actorOf(Props[Citizen])
  val daniel2 = system.actorOf(Props[Citizen])
  val daniel3 = system.actorOf(Props[Citizen])
  val daniel4 = system.actorOf(Props[Citizen])
  val daniel5 = system.actorOf(Props[Citizen])

  alice ! Vote(Candidate("Martin"))
  bob ! Vote(Candidate("Joanas"))
  charlie ! Vote(Candidate("Roland"))
  daniel ! Vote(Candidate("Roland"))
  daniel2 ! Vote(Candidate("Roland"))
  daniel3 ! Vote(Candidate("Roland"))
  daniel4 ! Vote(Candidate("Roland"))
  daniel5 ! Vote(Candidate("Roland"))

  val voteAggregator = system.actorOf(Props[VoteAggregator])

  voteAggregator ! AggregateVotes(Set(alice, bob, charlie, daniel,daniel2,daniel3,daniel4,daniel5))

  /*
  Print the status of the votes.
  (Candidate(Joanas),1)
  (Candidate(Martin),1)
  (Candidate(Roland),2)

   */


}

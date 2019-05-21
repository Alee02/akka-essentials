package com.dp.exercises

import akka.actor.Actor

object BankActorExample extends App {

  class BankAccount extends Actor {
    import BankAccount._
    var funds = 0

    override def receive: Receive = {
      case Deposit(amount) => {
        if (amount < 0) sender() ! TransactionFailure("Invalid deposit amount: cannot deposit negative amount")
        else {
          funds += amount
          sender() ! TransactionSuccess(s"Successfully deposited $amount")
        }
      }
      case Withdraw(amount) =>
        if (amount < 0) sender() ! TransactionFailure("Invalid withdraw amount: cannot withdraw negative amount")
        else if (amount > funds) sender() ! TransactionFailure("Insufficient funds")
        else {
          funds -= amount
          sender() ! TransactionSuccess(s"successfully withdrew $amount")
        }
      case Statement => sender() ! s"Your balance is $funds"
    }
  }

  object BankAccount {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object Statement

    case class TransactionSuccess(message: String)
    case class TransactionFailure(reason: String)
  }

  /*
  TESTING
   */

  // Bank account requires another actor to interact with as it sends messages back.
  // These would go to the dead letters actor (null actor) otherwise
}

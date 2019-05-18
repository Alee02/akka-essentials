package com.dp.scala_recap

object ThreadModelLimitation extends App {
  /*
  Rants
   */

  /**
    * #1: OOP Encapsulation is only valid in the SINGLE THREADED MODEL.
    */

  class BankAccount(@volatile private var amount: Int) {
    override def toString: String = "" + amount

    def withdraw(money: Int) = this.amount -= money

    def deposit(money: Int) = this.amount += money

    def getAmount = amount
  }
//
//  val account = new BankAccount(2000)
//  for(_ <- 1 to 1000) {
//    new Thread(() => account.withdraw(1)).start()
//  }
//
//  for(_ <- 1 to 1000) {
//    new Thread(() => account.deposit(1)).start()
//  }

//  println(account.getAmount)



  // OOP encapsulation is broken in a mutlithreaded env

  // syncrhonization! Locks to the rescue

  // deadlocks, livelocks

  // Solution: Need a data structure fully encapsulated and with no locks.

  /**
    * #2: Delegating something to a thread is a PAIN.
    */

  // not an executor service

  // its more like you have a running thread and you want to pass a runnable to that thread.

  var task: Runnable = null

  val runningThread: Thread = new Thread(() => {
    while (true) {
      while(task == null) {
        runningThread.synchronized{
          println("Background waiting for a task")
          runningThread.wait()
        }
      }

      task.synchronized{
        println("background i have a task")
        task.run()
        task = null
      }
    }
  })

  def delegateToBackgrounThread(r: Runnable) = {
    if (task == null) task = r
    runningThread.synchronized{
      runningThread.notify()
    }
  }

  runningThread.start()
  Thread.sleep(1000)
  delegateToBackgrounThread(() => println(42))
  Thread.sleep(1000)
  delegateToBackgrounThread(() => println("this should run in the background"))

  /**
    * Need a data structure which
    * can safely receive messages
    * can identify the sender
    * is easily identifiable
    * can guard against errors
     */

  /**
    * #3 tracing and dealing with erros in a multithredead env is a PITN
    */

  // 1M numbers in between 10 threads.
}

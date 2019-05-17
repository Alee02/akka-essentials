package com.dp.scala_recap

object AdvancedScalaRecap extends App {

  // partial functions
  // functions that operate only on a subset of a given domain.
  val partialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }
  // if anything else this function will throw an exception

  val pf = (x: Int) => x match {
    case 1 => 42
    case 2 => 65
    case 5 => 999
  }

  val function: (Int => Int) = partialFunction

  val modifiedList = List(1, 2, 3).map({
    case 1 => 42
    case _ => 0
  })

  // lifting
  val lifted = partialFunction.lift // total function Int => Option[Int]

  lifted(2) // Some(65)
  lifted(5000) // None

  // orElse
  val pfChain = partialFunction.orElse[Int, Int] {
    case 60 => 9000
  }

  pfChain(5) // 999 per partialFunction
  pfChain(60) // 9000
  try {
    pfChain(457)
  } catch {
    case _: MatchError => {
      println("Match Error")
    } // throw a MatchError
  }

  // type aliases
  type RecievedFunction = PartialFunction[Any, Unit]

  def receieve: RecievedFunction = {
    case 1 => println("hello")
    case _ => println("confused....")
  }

  // implicits

  implicit val timeout = 3000

  def setTimeout(f: () => Unit)(implicit timeout: Int) = f()

  setTimeout(() => println("timeout at "))

  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }


}

package com.dp.scala_recap

import scala.concurrent.Future

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

  implicit val timeout = 50000

  def setTimeout(f: () => Unit)(implicit timeout: Int) = f()

  setTimeout(() => println("timeout at "))(timeout)


  // implicit conversions
  // 1) implicit defs
  case class Person(name: String) {
    def greet = s"Hi, my name is $name"
  }

  implicit def fromStringToPerson(string: String): Person = Person(string)

  "Peter".greet

  implicit class Dog(name: String) {
    def bark = println("bark!")
  }

  "Lassie".bark
  // new Dog("Lassie").bark - automatically done by the compiler

  // organize
  implicit val inverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
  List(1,2,3).sorted // List(3,2,1)

  //imported scope
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future {
    println("hello, future")
  }

  // companion objects of the types included in the call
  object Person {
    implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => {
      a.name.compareTo(b.name) < 0
    })
  }

  List(Person("Bob"), Person("Alice")).sorted
  // List(Person(Alice), Person(Bob))

}

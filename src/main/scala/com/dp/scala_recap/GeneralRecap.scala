package com.dp.scala_recap

import java.nio.file.OpenOption

import scala.util.Try

object GeneralRecap extends App{

  val aCondition: Boolean = false

  var aVariable = 42
  aVariable += 1

  // expressions
  val aConditionVal = if (aCondition) 42 else 65

  // code block
  val aCodeBlock = {
    if (aCondition) 74
    56
  }

  // types
  // Unit
  val theUnit = println("Hello Scala") //dose something but doesn't return any value
  // all side effects return a Unit

  def aFunction(x: Int) = x + 1

  // recursion - TAIL recursion

  def factorial(n: Int, acc: Int): Int = {
    if (n <=0) acc
    else factorial(n - 1, acc * n)
  }

  // OOP
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch!")
  }

  //method notation
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  // anonymous clases
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar")
  }

  aCarnivore eat aDog

  // generics
  abstract class MyList[+A] // covariant + means
  // companion object of MyList and also a singleton object (pattern)
  object MyList

  // case classes
  case class Person(name: String, age: Int) /// a LOT in this course!

  // Exceptions
  val aPotentionalFailure = try {
    throw new RuntimeException("I'm innocent, I swear!") //Nothing
  } catch {
    case e: Exception => "I caught an exception!"
  } finally {
    println("Some logs")
  }


  // Functional Programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  val incremeneter = incrementer(42)
  // incrementer is a function syntactially but under the hood its an anonymous class

  val anonymousIncrementer = (x: Int) => x + 1
  // Int => Int === Function1[Int, Int]

  // FP is all about working with functions as first-class citizens
  // functions can be passed around as variables
  List(1,2,3).map(incrementer)

  // map = HOF as it takes a function as a parameter and returns a function
  val paris = for {
    num <- List(1,2,3,4)
    char <- List('a','b','c','d')
  } yield num + "-" + char

  // List(1,2,3,4).flatMap(num => List('a,'b','c','d').map(char => num + "-" + char))

  val anOption = Some(2)

  val aTry = Try {
    throw new RuntimeException
  }

  val unknown = 2
  val order = unknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unkown"
  }

  val bob = Person("Bob", 22)

  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
    case _ => "I don't know my name"
  }

  // ALL THE PATTERNS

}

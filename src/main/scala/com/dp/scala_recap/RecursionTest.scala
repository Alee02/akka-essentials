package com.dp.scala_recap

import scala.annotation.tailrec

object RecursionTest extends App{

  val sensorArrayA = List(15, -4, 56, 10, -23)
  val sensorArrayB = List(14, -9, 56, 14, -23)

  def getTotalDiff(arrayA: List[Int], arrayB: List[Int], acc: Int = 0): Int = {
    if (arrayA.isEmpty && arrayB.isEmpty) {
      acc
    } else {
      getTotalDiff(arrayA.tail, arrayB.tail, acc + (arrayA.head - arrayB.head).abs)
    }
  }

  def getTotalDiffMatchCase(arrayA: List[Int], arrayB: List[Int], acc: Int = 0): Int = {
    @tailrec
    def getTotalDiff(arrayA: List[Int], arrayB: List[Int], acc: Int = 0): Int = {
      (arrayA, arrayB) match {
        case (x :: xs, y :: ys) => getTotalDiff(xs, ys, acc + (x - y).abs)
        case _ => acc
      }
    }
    getTotalDiff(arrayA, arrayB)
  }

    println(getTotalDiff(sensorArrayA, sensorArrayB))
    println(getTotalDiffMatchCase(sensorArrayA, sensorArrayB))
}

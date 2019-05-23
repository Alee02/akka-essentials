package com.dp.actors

object InterviewTest extends App {
  def findLongSubSeq(s1: String, s2: String): String = {

    def findLongestSubSeq(s1: String, s2: String, acc: String): String = {

      if (s1.isEmpty && s2.isEmpty) acc
      else if (s1(0) == s2(0)) acc + s"${s1(0)}"
      else {
        findLongestSubSeq(s1.dropRight(1), s2.dropRight(1), acc)
      }
    }
    findLongestSubSeq(s1, s1, "")
  }
  println("Start")
  println(findLongSubSeq("ABAC", "CDAB"))
}

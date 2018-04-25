package com.marcastr0.prolog_utils

import org.scalatest.{FlatSpec, Matchers}
import spray.json._
import scala.io.Source
import com.marcastr0.prolog_utils.{JSONToProlog => Convert}

class JSONToPrologSpec extends FlatSpec with Matchers {

  "toProlog" should "return an empty string if the object is empty" in {
    val json = """{}"""
    Convert.toProlog(json.parseJson) shouldEqual ""
  }

  it should "convert an object with one predicate" in {
    val json ="""{ "parent": [ { "parent": "mike", "child": "marcia" } ] }"""
    val prolog =
      """% parent(Parent,Child).
        |parent(mike,marcia).""".stripMargin
    Convert.toProlog(json.parseJson) shouldEqual prolog
  }

  it should "return an empty string if the predicate has no facts" in {
    val json = """{ "parent": [] }"""
    Convert.toProlog(json.parseJson) shouldEqual ""
  }

  it should "convert atoms enclosed in single quotes" in {
    val json =
      """{ "airport": [
        |{ "code": "'YYZ'", "city": "'Toronto'" },
        |{ "code": "'SCL'", "city": "'Santiago'" } ] }""".stripMargin
    val prolog =
      """% airport(Code,City).
        |airport('YYZ','Toronto').
        |airport('SCL','Santiago').""".stripMargin
    Convert.toProlog(json.parseJson) shouldEqual prolog
  }

  it should "convert a predicate name to camelCase" in {
    val json = """{ "with_underscore": [ { "val": 1 } ], "with-dash": [ { "val": 2 } ] }"""
    val prolog =
      """% withUnderscore(Val).
        |withUnderscore(1).
        |% withDash(Val).
        |withDash(2).""".stripMargin
    Convert.toProlog(json.parseJson) shouldEqual prolog
  }

  it should "accept decimal numbers" in {
    val json = """{ "weather": [ { "city": "'Santiago'", "temperature": 23.5 } ] }"""
    val prolog =
      """% weather(City,Temperature).
        |weather('Santiago',23.5).""".stripMargin
    Convert.toProlog(json.parseJson) shouldEqual prolog
  }

  it should "convert larger examples" in {
    val json = Source.fromResource("test.json").getLines.mkString
    val prolog = Source.fromResource("test.pl").getLines.mkString("\n")
    Convert.toProlog(json.parseJson) shouldEqual prolog
  }
}

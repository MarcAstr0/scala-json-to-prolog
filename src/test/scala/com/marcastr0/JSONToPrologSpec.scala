package com.marcastr0

import org.scalatest.FlatSpec
import spray.json._
import com.marcastr0.{JSONToProlog => Convert}

class JSONToPrologSpec extends FlatSpec {

  "toProlog" should "should return an empty string if the object is empty" in {
    val json = """{}"""
    assert(Convert.toProlog(json.parseJson) === "")
  }

  it should "should convert an object with one predicate" in {
    val json ="""{ "parent": [ { "parent": "mike", "child": "marcia" } ] }"""
    val prolog =
      """% parent(Parent,Child).
        |parent(mike,marcia).""".stripMargin
    assert(Convert.toProlog(json.parseJson) === prolog)
  }

  it should "should return an empty string if the predicate has no facts" in {
    val json = """{ "parent": [] }"""
    assert(Convert.toProlog(json.parseJson) === "")
  }

  it should "should convert atoms enclosed in single quotes" in {
    val json =
      """{ "airport": [
        |{ "code": "'YYZ'", "city": "'Toronto'" },
        |{ "code": "'SCL'", "city": "'Santiago'" } ] }""".stripMargin
    val prolog =
      """% airport(Code,City).
        |airport('YYZ','Toronto').
        |airport('SCL','Santiago').""".stripMargin
    assert(Convert.toProlog(json.parseJson) === prolog)
  }

  it should "should convert a predicate name to camelCase" in {
    val json = """{ "with_underscore": [ { "val": 1 } ], "with-dash": [ { "val": 2 } ] }"""
    val prolog =
      """% withUnderscore(Val).
        |withUnderscore(1).
        |% withDash(Val).
        |withDash(2).""".stripMargin
    assert(Convert.toProlog(json.parseJson) === prolog)
  }

  it should "should accept decimal numbers" in {
    val json = """{ "weather": [ { "city": "'Santiago'", "temperature": 23.5 } ] }"""
    val prolog =
      """% weather(City,Temperature).
        |weather('Santiago',23.5).""".stripMargin
    assert(Convert.toProlog(json.parseJson) === prolog)
  }
}

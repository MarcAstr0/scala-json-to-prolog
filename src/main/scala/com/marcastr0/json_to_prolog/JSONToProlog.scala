package com.marcastr0.json_to_prolog

import spray.json._
import DefaultJsonProtocol._
import net.liftweb.util.StringHelpers.{camelify, camelifyMethod}

object JSONToProlog {

  def toProlog(json: JsValue): String = {
    val data = json.convertTo[Map[String, Seq[Map[String, JsValue]]]]
    val predicates = data.keys
    val result = for {
      predicate <- predicates
      predicateName = camelifyMethod(predicate.replaceAll("-", "_"))
      parameters = data.getOrElse(predicate, Seq()).headOption.getOrElse(Map()).keysIterator.map(camelify).mkString(",")
      comment = if (!parameters.isEmpty) s"% $predicateName($parameters)." else ""
      facts = data.getOrElse(predicate, Seq()).map(x => s"$predicateName(${x.values.map(JsValue2String).mkString(",")}).")
    } yield (List(comment) ++ facts).mkString("\n")
    result.mkString("\n")
  }

  def JsValue2String(string: JsValue): String = {
    string match {
      case s: JsString => s.value
      case o => o.toString()
    }
  }
}

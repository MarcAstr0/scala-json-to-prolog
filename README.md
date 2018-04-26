[![Download](https://api.bintray.com/packages/marcastr0/maven/json-to-prolog/images/download.svg) ](https://bintray.com/marcastr0/maven/json-to-prolog/_latestVersion)

# scala-json-to-prolog

Simple Scala library for converting JSON objects to Prolog predicates. It takes [spray-json](https://github.com/spray/spray-json) parsed JSON objects and returns a string with the Prolog predicates.

For example, the following object:

```json
{
  "parent": [
    { "parent": "mike", "child": "greg" },
    { "parent": "mike", "child": "marcia" },
    { "parent": "mike", "child": "peter" },
    { "parent": "mike", "child": "jan" },
    { "parent": "mike", "child": "bobby" },
    { "parent": "mike", "child": "cindy" },
    { "parent": "carol", "child": "greg" },
    { "parent": "carol", "child": "marcia" },
    { "parent": "carol", "child": "peter" },
    { "parent": "carol", "child": "jan" },
    { "parent": "carol", "child": "bobby" },
    { "parent": "carol", "child": "cindy" }
  ]
}
```

Will convert to:

```prolog
% parent(Parent, Child).
parent(mike, greg).
parent(mike, marcia).
parent(mike, peter).
parent(mike, jan).
parent(mike, bobby).
parent(mike, cindy).
parent(carol, greg).
parent(carol, marcia).
parent(carol, peter).
parent(carol, jan).
parent(carol, bobby).
parent(carol, cindy).
```

### Installation

First, add this to your build.sbt file:

```sbtshell
resolvers += Resolver.bintrayRepo("marcastr0","maven")
```

Then add the following to your dependencies:

```sbtshell
libraryDependencies ++= Seq(
  "com.marcastr0" %% "json-to-prolog" % "0.1",
  "io.spray"      %% "spray-json"  % "1.3.3"
)
```

### Usage

Here is an example usage:

```scala
import com.marcastr0.prolog_utils.JSONToProlog
import spray.json._

object Test {
  def main(args: Array[String]): Unit = {
    val json ="""{ "parent": [ { "parent": "mike", "child": "marcia" } ] }"""
    println(JSONToProlog.toProlog(json.parseJson))
    /* Will print
    % parent(Parent,Child).
    parent(mike,marcia).
     */
  }
}
```

The JSON object must follow the following format:

```json
{
  "predicate1": [
    { "argument1": "someValue", ..., "argumentN": "anotherValue"},
    ...,
    { "argument1": "andAnotherValue", ..., "argumentN": "etc."}
  ],
  ...,
  "predicateN": [
    ...
  ]
}
```

Each property of the main object describes a predicate, and each predicate has an array (list) of
facts represented as objects, with the predicate's arguments as properties.

### TODO

* Return [JPL](https://jpl7.org/) objects instead of strings.
* Add a method for converting _from_ Prolog to JSON.
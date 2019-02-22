package io.github.tmiskow.sqlplusplus.interpreter

case class Context(variables: Seq[String], environments: Seq[Environment])
package io.github.tmiskow.sqlplusplus.interpreter

final case class InterpreterException(private val message: String, private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

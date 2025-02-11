package com.github.swagger.akka.javadsl

import java.util

import scala.collection.JavaConverters._
import io.swagger.models.{ExternalDocs, Info, Scheme}
import io.swagger.models.auth.SecuritySchemeDefinition
import com.github.swagger.akka.model._

trait SwaggerGenerator {
  def apiClasses: util.Set[Class[_]]
  def host: String = ""
  def basePath: String = "/"
  def apiDocsPath: String = "api-docs"
  def info: Info = new Info()
  def schemes: util.List[Scheme] = List(Scheme.HTTP).asJava
  def securitySchemeDefinitions: util.Map[String, SecuritySchemeDefinition] = util.Collections.emptyMap()
  def externalDocs: util.Optional[ExternalDocs] = util.Optional.empty()
  def vendorExtensions: util.Map[String, Object] = util.Collections.emptyMap()
  def unwantedDefinitions: util.List[String] = util.Collections.emptyList()

  private[javadsl] lazy val converter = new Converter(this)

  def generateSwaggerJson: String = converter.generateSwaggerJson
  def generateSwaggerYaml: String = converter.generateSwaggerYaml
}

private class Converter(javaGenerator: SwaggerGenerator) extends com.github.swagger.akka.SwaggerGenerator {
  import com.github.swagger.akka.model.swagger2scala
  override def apiClasses: Set[Class[_]] = setAsScala(javaGenerator.apiClasses)
  override def host: String = javaGenerator.host
  override def basePath: String = javaGenerator.basePath
  override def apiDocsPath: String = javaGenerator.apiDocsPath
  override def info: com.github.swagger.akka.model.Info = javaGenerator.info
  override def schemes: List[Scheme] = listAsScala(javaGenerator.schemes)
  override def securitySchemeDefinitions: Map[String, SecuritySchemeDefinition] = mapAsScala(javaGenerator.securitySchemeDefinitions)
  override def externalDocs: Option[ExternalDocs] = optionalAsScala(javaGenerator.externalDocs)
  override def vendorExtensions: Map[String, Object] = mapAsScala(javaGenerator.vendorExtensions)
  override def unwantedDefinitions: Seq[String] = listAsScala(javaGenerator.unwantedDefinitions)
}

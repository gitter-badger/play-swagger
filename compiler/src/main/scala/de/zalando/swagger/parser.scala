package de.zalando.swagger

import java.io.File

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import de.zalando.swagger.model.SwaggerModel

trait Parser {
  def parse(file: File): SwaggerModel
}

private[swagger] abstract class SwaggerParser extends Parser {
  def factory: JsonFactory
  def parse(file: File): SwaggerModel = {
    val mapper = new ObjectMapper(factory)
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true)
    mapper.readValue(file, classOf[SwaggerModel])
  }
}

object YamlParser extends SwaggerParser {
  val factory = new YAMLFactory()
}

object JsonParser extends SwaggerParser {
  val factory = new JsonFactory()
}


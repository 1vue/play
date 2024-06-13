package Utils

import play.api.libs.json.{Json, OFormat, OWrites, Writes}

case class result1[T](code: Int, msg: String, data: Option[T])

object result1 {
//  implicit def format[T: OFormat]: OFormat[result1[T]] = Json.format[result1[T]]
  implicit def writes[T: OWrites]: OWrites[result1[T]] = Json.writes[result1[T]]

  implicit val result1StringWrites: Writes[result1[String]] = Json.writes[result1[String]]


  def success[T](data: T): result1[T] = result1(1, "success", Some(data))
  def error[T](msg: String): result1[T] = result1(0, msg, None)
}

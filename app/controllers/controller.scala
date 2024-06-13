package controllers
import Model.User

import javax.inject._
import play.api._
import play.api.libs.json.{JsError, Json, OWrites}
import play.api.mvc._
import Utils.result1
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites



@Singleton
class controller @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val logger :Logger= Logger(this.getClass)
  def bye()=Action{
    implicit request:Request[AnyContent]=>Ok("Welcome to Play hhhhh!")
  }


  def hello() = Action(parse.formUrlEncoded) { request =>
    val formData = request.body // 解析表单数据
    // 获取表单字段 name 和 age 的值
    val name = formData.get("name").flatMap(_.headOption)
    val age = formData.get("age").flatMap(_.headOption)
println(formData)
    // 检查是否缺少必填参数
    (name, age) match {
      case (Some(n), Some(a)) =>{
val res=result1.success(n)
        Ok(Json.toJson(res))
      }
      case _ => BadRequest("Missing required parameters")
    }
  }

  def hello3() = Action(parse.multipartFormData) { request =>

    request.body.dataParts.get("name").flatMap(_.headOption) match {
      case Some(name) =>
        request.body.dataParts.get("age").flatMap(_.headOption) match {
          case Some(age) =>
            {
              val res=result1.success(name)

Ok(Json.toJson(res))
//              Ok
            }
          case None =>
            BadRequest("Missing 'age' parameter")
        }
      case None =>
        BadRequest("Missing 'name' parameter")
    }

  }

def jsonparse()=Action(parse.json){request =>
  val userJson=request.body
 val user=userJson.validate[User]

  user.fold(
    errors => {
      BadRequest(Json.obj("message" -> "Invalid JSON format", "errors" -> JsError.toJson(errors)))
    },
    user => {
      // 此处处理成功解析的用户对象
//      println(s"Received User: ${user.name}, ${user.age}")
      logger.info(s"Received User: ${user.name}, ${user.age}")
     val res=result1.success(user)
    Ok(Json.toJson(res))
    }


  )

}

}

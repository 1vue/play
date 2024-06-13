package Model

import com.typesafe.sslconfig.ssl.FakeChainedKeyStore.User
import play.api.libs.json.{Json, OFormat}

case class User(name:String,age:Int)

object User{
  implicit val userDataFormat: OFormat[User] = Json.format[User]
}
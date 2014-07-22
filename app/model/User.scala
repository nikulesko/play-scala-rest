package model

import org.apache.commons.lang.builder.ToStringBuilder
import org.squeryl.{Schema, KeyedEntity}
import play.api.libs.json.{JsString, JsNumber, JsArray, JsObject}

class User(var id: Long, var name: String, var email: String, var age: Int) extends KeyedEntity[Long] {
  def this() = this(0, "", "", 0)

  override def toString = {
    new ToStringBuilder(this).
      append("id", id).
      append("name", name).
      append("email", email).
      append("age", age)
    toString
  }
}

object User {
  def toJsonLowLevel(users: List[User]): JsObject = {
    JsObject(
      "users" -> JsArray(
        users.map {user => toJsonLowLevel(user)}
      ) :: Nil
    )
  }

  def toJsonLowLevel(user: User) = {
    JsObject(
      "id" -> JsNumber(user.id) ::
        "name" -> JsString(user.name.replace("\"", "")) ::
        "email" -> JsString(user.email.replace("\"", "")) ::
        "age" -> JsNumber(user.age) :: Nil
    )
  }
}

object Db extends Schema {
  val users = table[User]("users")
}



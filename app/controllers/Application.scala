package controllers

import model.{Db, User}
import org.squeryl.PrimitiveTypeMode._
import play.api.mvc._

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def addUser() = Action(parse.json) { implicit request =>
    val name = request.body.\("name").toString()
    val email = request.body.\("email").toString()
    val age = request.body.\("age").toString().toInt

    transaction {
      Db.users.insert(new User(0, name, email, age))
      Created("{status: \"Ok\"}")
    }
  }

  def allUsers = Action { implicit request =>
    transaction {
      Ok(User.toJsonLowLevel(from(Db.users)(s => select(s)).toList))
    }
  }

  def deleteUser() = Action(parse.json) { implicit request =>
    val id = request.body.\("id").toString().toLong

    transaction {
      if (Db.users.deleteWhere(user => user.id === id) > 0) {
        Ok("{status: \"Ok\"}")
      } else {
        Ok("{status: \"Not found\"}")
      }
    }
  }

  def editUser() = Action(parse.json) { implicit request =>
    val name = request.body.\("name").toString()
    val email = request.body.\("email").toString()
    val age = request.body.\("age").toString().toInt
    val id = request.body.\("id").toString().toLong

    transaction {
      try {
        Db.users.update(new User(id, name, email, age))
        Ok("{status: \"Ok\"}")
      } catch {
        case re: RuntimeException => Ok("{status: \"Update failed\"}")
      }
    }
  }

  def getUserById(id: String) = Action { implicit request =>
    transaction {
      Ok(User.toJsonLowLevel(List(Db.users.where(s => s.id.toString === id).head)))
    }
  }
}
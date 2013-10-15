package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import model._
import views._

object Application extends Controller {
  val matNoRe = """\d{7}""".r
  
  val registrationForm = Form(
    mapping(
      "matriculationnumber" -> text.verifying("no valid matriculation number", matNo => matNo match {
        case matNoRe() => true
        case _ => false
      }),
      "firstname" -> text,
      "lastname" -> text,
      "email" -> email,
      "pick1" -> number,
      "pick2" -> number,
      "pick3" -> number,
      "attend_to_rdb" -> boolean,
      "attend_to_sql" -> boolean,
      "teammate" -> text.verifying("no valid matriculation number", matNo => matNo match {
        case matNoRe() => true
        case "" => true
        case _ => false
      })
    )(RegistrationData.apply)(RegistrationData.unapply).verifying("Group picks must not be equal", regData => regData match {
      case RegistrationData(_, _, _, _, p1, p2, p3, rdb, sql, _) =>
        List(p1, p2, p3).distinct.size == 3 && (rdb || sql)
      case _ => false
    })
  )

  def index = Action { implicit request => 
    Ok(views.html.index(RDBGroup.list(), registrationForm))
  }
  
  def register = Action { implicit request =>
    registrationForm.bindFromRequest.fold(
      formWithErrors => Redirect("/").flashing("status" -> "form_error"),
      value => {
        try {
            value.save()
            Redirect("/").flashing("status" -> "register_success")
        } catch {
            case(th: Throwable) => Redirect("/").flashing("status" -> "sql_error")
        }
      }
    )
  } 
}

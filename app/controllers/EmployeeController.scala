package controllers

import com.google.inject.Inject
import models.Employee
import org.slf4j.LoggerFactory
import play.api.Logger
import play.api.i18n._
import play.api.libs.json.Json._
import play.api.libs.json.{JsError, JsObject, JsValue, Json}
import play.api.mvc._
import repository.EmployeeRepository
import utils.Constants
import utils.JsonFormat._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Handles all requests related to employee
  */
class EmployeeController @Inject()(
                                    cc: ControllerComponents,
                                    empRepository: EmployeeRepository,
                                    langs: Langs,
                                    messagesApi: MessagesApi
                                  )(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  import Constants._

  val logger = LoggerFactory.getLogger(this.getClass)

  implicit val lang: Lang = langs.availables.head

  /**
    * Handles request for getting all employee from the database
    */
  def list: Action[AnyContent] =
    Action.async {
      empRepository.getAll().map { res =>
        logger.info("Emp list: " + res)
        Ok(successResponse(Json.toJson(res), messagesApi("emp.success.empList")))
      }
    }

  /**
    * Handles request for creation of new employee
    */
  def create: Action[JsValue] =
    Action.async(parse.json) { request =>
      logger.info("Employee Json ===> " + request.body)
      request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
        empRepository.insert(emp).map { createdEmpId =>
          Ok(successResponse(Json.toJson(Map("id" -> createdEmpId)), messagesApi("emp.success.created")))
        }
      })
    }

  /**
    * Handles request for deletion of existing employee by employee_id
    */
  def delete(empId: Int): Action[AnyContent] =
    Action.async { _ =>
      empRepository.delete(empId).map { _ =>
        Ok(successResponse(Json.toJson("{}"), messagesApi("emp.success.deleted")))
      }
    }

  /**
    * Handles request for get employee details for editing
    */
  def edit(empId: Int): Action[AnyContent] =
    Action.async { _ =>
      empRepository.getById(empId).map { empOpt =>
        empOpt.fold(Ok(errorResponse(Json.toJson("{}"), messagesApi("emp.error.empNotExist"))))(emp => Ok(
          successResponse(Json.toJson(emp), messagesApi("emp.success.employee"))))
      }
    }

  private def successResponse(data: JsValue, message: String): JsObject = {
    obj("status" -> SUCCESS, "data" -> data, "msg" -> message)
  }

  private def errorResponse(data: JsValue, message: String) = {
    obj("status" -> ERROR, "data" -> data, "msg" -> message)
  }

  /**
    * Handles request for update existing employee
    */
  def update: Action[JsValue] =
    Action.async(parse.json) { request =>
      logger.info("Employee Json ===> " + request.body)
      request.body.validate[Employee].fold(error => Future.successful(BadRequest(JsError.toJson(error))), { emp =>
        empRepository.update(emp).map { _ => Ok(successResponse(Json.toJson("{}"), messagesApi("emp.success.updated"))) }
      })
    }

}


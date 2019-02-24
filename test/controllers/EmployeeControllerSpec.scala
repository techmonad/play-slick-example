package controllers


import models.Employee
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.i18n.{DefaultLangs, MessagesApi}
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication, _}
import repository.EmployeeRepository
import utils.JsonFormat._

import scala.concurrent.{ExecutionContext, Future}


class EmployeeControllerSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerTest {

  implicit val mockedRepo: EmployeeRepository = mock[EmployeeRepository]


  "EmployeeController " should {

    "create a employee" in new WithEmpApplication() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant")
      when(mockedRepo.insert(emp)) thenReturn Future.successful(1)
      val result = employeeController.create().apply(FakeRequest().withBody(Json.toJson(emp)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"status":"success","data":{"id":1},"msg":"Employee has been created successfully."}"""
    }

    "update a employee" in new WithEmpApplication() {
      val updatedEmp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant", Some(1))
      when(mockedRepo.update(updatedEmp)) thenReturn Future.successful(1)
      val result = employeeController.update().apply(FakeRequest().withBody(Json.toJson(updatedEmp)))
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"status":"success","data":"{}","msg":"Employee has been updated successfully."}"""
    }

    "edit a employee" in new WithEmpApplication() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant", Some(1))
      when(mockedRepo.getById(1)) thenReturn Future.successful(Some(emp))
      val result = employeeController.edit(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"status":"success","data":{"name":"jaz","email":"jaz@bar.com","companyName":"ABC solution","position":"Senior Consultant","id":1},"msg":"Getting Employee successfully."}"""
    }

    "delete a employee" in new WithEmpApplication() {
      when(mockedRepo.delete(1)) thenReturn Future.successful(1)
      val result = employeeController.delete(1).apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"status":"success","data":"{}","msg":"Employee has been deleted successfully."}"""
    }

    "get all list" in new WithEmpApplication() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant", Some(1))
      when(mockedRepo.getAll()) thenReturn Future.successful(List(emp))
      val result = employeeController.list().apply(FakeRequest())
      val resultAsString = contentAsString(result)
      resultAsString mustBe """{"status":"success","data":[{"name":"jaz","email":"jaz@bar.com","companyName":"ABC solution","position":"Senior Consultant","id":1}],"msg":"Getting Employee list successfully."}"""
    }

  }

}

class WithEmpApplication(implicit mockedRepo: EmployeeRepository) extends WithApplication with Injecting {

  implicit val ec = inject[ExecutionContext]

  val messagesApi = inject[MessagesApi]

  val employeeController: EmployeeController =
    new EmployeeController(
      stubControllerComponents(),
      mockedRepo,
      new DefaultLangs(),
      messagesApi
    )

}

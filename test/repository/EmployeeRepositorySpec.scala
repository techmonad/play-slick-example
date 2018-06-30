package repository


import play.api.Application
import play.api.test.{PlaySpecification, WithApplication}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class EmployeeRepositorySpec extends PlaySpecification {

  import models._

  "Employee repository" should {

    def empRepo(implicit app: Application) = Application.instanceCache[EmployeeRepository].apply(app)


    "get all rows" in new WithApplication() {
      val result = await(empRepo.getAll)
      result.length === 3
      result.head.name === "Bob"
    }

    "get single rows" in new WithApplication() {
      val result = await(empRepo.getById(1))
      result.isDefined === true
      result.get.name === "Bob"
    }

    "insert a row" in new WithApplication() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant")
      val empId = await(empRepo.insert(emp))
      empId === 4
    }

    "insert multiple rows" in new WithApplication() {
      val list = List(Employee("Sam", "sam@abc.com", "Foo LLP", "Consultant"))
      val result = empRepo.insertAll(list)
      await(result) === Seq(4)
    }

    "update a row" in new WithApplication() {
      val result = await(empRepo.update(Employee("BOB", "bob@abc.com", "ABC Solution", "Consultant", Some(1))))
      result === 1
    }

    "delete a row" in new WithApplication() {
      val result = await(empRepo.delete(1))
      result === 1
    }
  }

  def await[T](v: Future[T]): T = Await.result(v, Duration.Inf)

}

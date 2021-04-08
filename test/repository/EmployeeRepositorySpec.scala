package repository


import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Helpers._
import play.api.test.{Injecting, WithApplication}

class EmployeeRepositorySpec extends PlaySpec with GuiceOneAppPerTest {

  import models._

  "Employee repository" should {

    "get all rows" in new WithEmpRepository() {
      val result = await(empRepo.getAll())
      result.length mustBe 3
      result.head.name mustBe "Bob"
    }

    "get single rows" in new WithEmpRepository() {
      val result = await(empRepo.getById(1))
      result.isDefined mustBe true
      result.get.name mustBe "Bob"
    }

    "insert a row" in new WithEmpRepository() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant")
      val empId = await(empRepo.insert(emp))
      empId mustBe 4
    }

    "insert multiple rows" in new WithEmpRepository() {
      val list = List(Employee("Sam", "sam@abc.com", "Foo LLP", "Consultant"))
      val result = empRepo.insertAll(list)
      await(result) mustBe Seq(4)
    }

    "update a row" in new WithEmpRepository() {
      val result = await(empRepo.update(Employee("BOB", "bob@abc.com", "ABC Solution", "Consultant", Some(1))))
      result mustBe 1
    }

    "delete a row" in new WithEmpRepository() {
      val result = await(empRepo.delete(1))
      result mustBe 1
    }
  }


}

trait WithEmpRepository extends WithApplication with Injecting {

  val empRepo = inject[EmployeeRepository]
}
